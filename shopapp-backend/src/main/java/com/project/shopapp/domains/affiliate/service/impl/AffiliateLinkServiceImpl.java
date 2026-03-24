// --- service/impl/AffiliateLinkServiceImpl.java ---
package com.project.shopapp.domains.affiliate.service.impl;

import com.project.shopapp.domains.affiliate.dto.request.AffiliateLinkCreateRequest;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateLinkResponse;
import com.project.shopapp.domains.affiliate.entity.AffiliateLink;
import com.project.shopapp.domains.affiliate.event.AffiliateLinkClickedEvent;
import com.project.shopapp.domains.affiliate.mapper.AffiliateLinkMapper;
import com.project.shopapp.domains.affiliate.repository.AffiliateLinkRepository;
import com.project.shopapp.domains.affiliate.service.AffiliateLinkService;
import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AffiliateLinkServiceImpl implements AffiliateLinkService {

    private final AffiliateLinkRepository linkRepo;
    private final AffiliateLinkMapper linkMapper;
    private final ProductInternalApi productApi;
    private final ApplicationEventPublisher eventPublisher;

    private static final String REDIRECT_BASE_URL = "https://shopapp.vn/products/"; // URL của Website/Frontend
    private static final String SHORT_URL_DOMAIN = "https://shopapp.vn/go/";

    @Override
    @Transactional
    public AffiliateLinkResponse generateLink(Integer userId, AffiliateLinkCreateRequest request) {
        // Đảm bảo Product tồn tại
        ProductBasicDto product = productApi.getProductBasicInfo(request.getProductId());
        if (product == null) throw new DataNotFoundException("Sản phẩm không tồn tại");

        // Tránh 1 user tạo nhiều link cho cùng 1 SP
        Optional<AffiliateLink> existingLink = linkRepo.findByUserIdAndProductIdAndIsDeleted(userId, request.getProductId(), 0L);
        if (existingLink.isPresent()) {
            return buildResponse(existingLink.get(), product);
        }

        // Sinh mã ngẫu nhiên (6 ký tự)
        String uniqueCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        while (linkRepo.existsByCodeAndIsDeleted(uniqueCode, 0L)) {
            uniqueCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        }

        AffiliateLink newLink = AffiliateLink.builder()
                .userId(userId)
                .productId(request.getProductId())
                .code(uniqueCode)
                .commissionRate(request.getCustomCommissionRate() != null ? BigDecimal.valueOf(request.getCustomCommissionRate()) : null)
                .build();

        return buildResponse(linkRepo.save(newLink), product);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AffiliateLinkResponse> getMyLinks(Integer userId, int page, int size) {
        Page<AffiliateLink> pagedResult = linkRepo.findByUserIdAndIsDeleted(
                userId, 0L, PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(entity -> {
            ProductBasicDto prod = productApi.getProductBasicInfo(entity.getProductId());
            return buildResponse(entity, prod);
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public String resolveRedirectUrl(String code, String ipAddress, String userAgent) {
        AffiliateLink link = linkRepo.findByCodeAndIsDeleted(code, 0L)
                .orElseThrow(() -> new DataNotFoundException("Link Affiliate không hợp lệ hoặc đã hết hạn"));

        if (!link.getIsActive()) {
            return "https://shopapp.vn"; // Redirect về trang chủ nếu link bị khóa
        }

        // Bắn Event để tăng Click ngầm, Tránh làm chậm quá trình Redirect của End User
        eventPublisher.publishEvent(new AffiliateLinkClickedEvent(code, ipAddress, userAgent));

        ProductBasicDto prod = productApi.getProductBasicInfo(link.getProductId());
        // Frontend sẽ bóc ref code từ URL để lưu vào LocalStorage/Cookie
        return REDIRECT_BASE_URL + prod.getSlug() + "?ref=" + code;
    }

    // --- Lắng nghe Event tăng Click ---
    @Async("affiliateTaskExecutor")
    @EventListener
    @Transactional
    public void trackClickAsync(AffiliateLinkClickedEvent event) {
        // Có thể filter Fraud bằng IP ở đây (VD: Cùng 1 IP click 100 lần trong 1 phút thì chặn)
        linkRepo.incrementClick(event.getCode());
    }

    private AffiliateLinkResponse buildResponse(AffiliateLink link, ProductBasicDto product) {
        AffiliateLinkResponse response = linkMapper.toDto(link);
        response.setShortUrl(SHORT_URL_DOMAIN + link.getCode());
        if (product != null) {
            response.setProductName(product.getName());
        }
        return response;
    }
}