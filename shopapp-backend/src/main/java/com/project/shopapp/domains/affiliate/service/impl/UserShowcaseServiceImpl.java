// --- service/impl/UserShowcaseServiceImpl.java ---
package com.project.shopapp.domains.affiliate.service.impl;

import com.project.shopapp.domains.affiliate.constant.AffiliateConstants;
import com.project.shopapp.domains.affiliate.dto.request.AffiliateLinkCreateRequest;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseAddRequest;
import com.project.shopapp.domains.affiliate.dto.request.ShowcaseReorderRequest;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateLinkResponse;
import com.project.shopapp.domains.affiliate.dto.response.ShowcaseItemResponse;
import com.project.shopapp.domains.affiliate.entity.UserShowcaseItem;
import com.project.shopapp.domains.affiliate.event.ShowcaseItemAddedEvent;
import com.project.shopapp.domains.affiliate.mapper.ShowcaseItemMapper;
import com.project.shopapp.domains.affiliate.repository.UserShowcaseItemRepository;
import com.project.shopapp.domains.affiliate.service.AffiliateLinkService;
import com.project.shopapp.domains.affiliate.service.UserShowcaseService;
import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductBasicDto;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserShowcaseServiceImpl implements UserShowcaseService {

    private final UserShowcaseItemRepository showcaseRepo;
    private final ShowcaseItemMapper showcaseMapper;
    private final ProductInternalApi productApi;
    private final AffiliateLinkService affiliateLinkService; // Inject service cùng module để gen link
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ShowcaseItemResponse addItemToShowcase(Integer userId, ShowcaseAddRequest request) {
        if (showcaseRepo.countByUserIdAndIsDeleted(userId, 0L) >= AffiliateConstants.MAX_SHOWCASE_ITEMS) {
            throw new ConflictException("Tủ đồ của bạn đã đạt giới hạn tối đa (" + AffiliateConstants.MAX_SHOWCASE_ITEMS + " sản phẩm).");
        }

        if (showcaseRepo.existsByUserIdAndProductIdAndIsDeleted(userId, request.getProductId(), 0L)) {
            throw new ConflictException("Sản phẩm đã tồn tại trong tủ đồ.");
        }

        ProductBasicDto product = productApi.getProductBasicInfo(request.getProductId());
        if (product == null) throw new DataNotFoundException("Sản phẩm không tồn tại.");

        // TỰ ĐỘNG GEN AFFILIATE LINK CHO SẢN PHẨM NÀY NẾU KOC CHƯA CÓ
        AffiliateLinkCreateRequest linkReq = new AffiliateLinkCreateRequest();
        linkReq.setProductId(request.getProductId());
        AffiliateLinkResponse linkRes = affiliateLinkService.generateLink(userId, linkReq);

        UserShowcaseItem item = showcaseMapper.toEntityFromRequest(request);
        item.setUserId(userId);
        item.setAffiliateLinkId(linkRes.getId());
        UserShowcaseItem saved = showcaseRepo.save(item);

        eventPublisher.publishEvent(new ShowcaseItemAddedEvent(userId, request.getProductId()));

        return hydrateItem(saved, product, linkRes.getShortUrl());
    }

    @Override
    @Transactional
    public void removeItemFromShowcase(Integer userId, Integer itemId) {
        UserShowcaseItem item = showcaseRepo.findByIdAndUserIdAndIsDeleted(itemId, userId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy sản phẩm trong tủ đồ"));
        item.setIsDeleted(System.currentTimeMillis());
        showcaseRepo.save(item);
    }

    @Override
    @Transactional
    public void toggleItemVisibility(Integer userId, Integer itemId, boolean isHidden) {
        UserShowcaseItem item = showcaseRepo.findByIdAndUserIdAndIsDeleted(itemId, userId, 0L).orElseThrow();
        item.setIsHidden(isHidden);
        showcaseRepo.save(item);
    }

    @Override
    @Transactional
    public void reorderShowcase(Integer userId, ShowcaseReorderRequest request) {
        int order = 1;
        for (Integer itemId : request.getShowcaseItemIds()) {
            final int currentOrder = order; // Tạo 1 bản sao không bị thay đổi (effectively final)
            showcaseRepo.findByIdAndUserIdAndIsDeleted(itemId, userId, 0L).ifPresent(item -> {
                item.setDisplayOrder(currentOrder); // Dùng bản sao này
                showcaseRepo.save(item);
            });
            order++;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ShowcaseItemResponse> getMyShowcase(Integer userId, int page, int size) {
        Page<UserShowcaseItem> pagedResult = showcaseRepo.findByUserIdAndIsDeletedOrderByDisplayOrderAscCreatedAtDesc(
                userId, 0L, PageRequest.of(page - 1, size)
        );
        return PageResponse.of(pagedResult.map(this::hydrateItemFromDb));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ShowcaseItemResponse> getPublicShowcase(Integer kocUserId, int page, int size) {
        Page<UserShowcaseItem> pagedResult = showcaseRepo.findByUserIdAndIsHiddenFalseAndIsDeletedOrderByDisplayOrderAscCreatedAtDesc(
                kocUserId, 0L, PageRequest.of(page - 1, size)
        );
        return PageResponse.of(pagedResult.map(this::hydrateItemFromDb));
    }

    // Hydration Logic
    private ShowcaseItemResponse hydrateItemFromDb(UserShowcaseItem item) {
        ProductBasicDto prod = productApi.getProductBasicInfo(item.getProductId());
        String shortUrl = item.getAffiliateLink() != null ? "https://shopapp.vn/go/" + item.getAffiliateLink().getCode() : null;
        return hydrateItem(item, prod, shortUrl);
    }

    private ShowcaseItemResponse hydrateItem(UserShowcaseItem item, ProductBasicDto prod, String shortUrl) {
        ShowcaseItemResponse dto = showcaseMapper.toDto(item);
        if (prod != null) {
            dto.setProductName(prod.getName());
            dto.setProductPrice(prod.getPrice().toString());
            dto.setProductThumbnail(prod.getThumbnail());
        }
        dto.setAffiliateShortUrl(shortUrl);
        return dto;
    }
}