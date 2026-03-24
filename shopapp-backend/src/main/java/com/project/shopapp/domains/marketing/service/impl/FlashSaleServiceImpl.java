// --- service/impl/FlashSaleServiceImpl.java ---
package com.project.shopapp.domains.marketing.service.impl;

import com.project.shopapp.domains.marketing.dto.request.FlashSaleCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleResponse;
import com.project.shopapp.domains.marketing.entity.FlashSale;
import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import com.project.shopapp.domains.marketing.event.FlashSaleStatusTransitionEvent;
import com.project.shopapp.domains.marketing.mapper.FlashSaleMapper;
import com.project.shopapp.domains.marketing.repository.FlashSaleRepository;
import com.project.shopapp.domains.marketing.service.FlashSaleService;
import com.project.shopapp.domains.marketing.specification.FlashSaleSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlashSaleServiceImpl implements FlashSaleService {

    private final FlashSaleRepository flashSaleRepo;
    private final FlashSaleMapper flashSaleMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FlashSaleResponse> searchSales(Integer shopId, String statusStr, int page, int size) {
        FlashSaleStatus status = statusStr != null ? FlashSaleStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<FlashSale> pagedResult = flashSaleRepo.findAll(
                FlashSaleSpecification.search(shopId, status),
                PageRequest.of(page - 1, size, Sort.by("startTime").descending())
        );
        return PageResponse.of(pagedResult.map(flashSaleMapper::toDto));
    }

    @Override
    @Transactional
    public FlashSaleResponse createFlashSale(Integer currentUserId, Integer shopId, FlashSaleCreateRequest request) {
        // Chống đụng độ thời gian: 1 Shop không thể chạy 2 Flash Sale cùng lúc
        // (Nếu shopId = null là của Platform, ta cho phép nhiều Flash Sale chạy song song theo Theme khác nhau)
        if (shopId != null) {
            long overlaps = flashSaleRepo.countOverlappingSales(shopId, request.getStartTime(), request.getEndTime());
            if (overlaps > 0) {
                throw new ConflictException("Thời gian diễn ra bị trùng lặp với một đợt Flash Sale khác của bạn.");
            }
        }

        FlashSale sale = flashSaleMapper.toEntityFromRequest(request);
        sale.setShopId(shopId);
        sale.setCreatedBy(currentUserId);

        return flashSaleMapper.toDto(flashSaleRepo.save(sale));
    }

    @Override
    @Transactional
    public void cancelFlashSale(Integer currentUserId, Integer flashSaleId) {
        FlashSale sale = flashSaleRepo.findById(flashSaleId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy Flash Sale"));

        if (sale.getStatus() == FlashSaleStatus.ENDED) {
            throw new ConflictException("Không thể hủy chương trình đã kết thúc.");
        }

        FlashSaleStatus oldStatus = sale.getStatus();
        sale.setStatus(FlashSaleStatus.CANCELLED);
        sale.setUpdatedBy(currentUserId);
        flashSaleRepo.save(sale);

        eventPublisher.publishEvent(new FlashSaleStatusTransitionEvent(sale.getId(), oldStatus, FlashSaleStatus.CANCELLED));
    }
}