package com.project.shopapp.domains.marketing.api.impl;

import com.project.shopapp.domains.marketing.api.FlashSaleItemInternalApi;
import com.project.shopapp.domains.marketing.dto.nested.FlashSaleItemBasicDto;
import com.project.shopapp.domains.marketing.repository.FlashSaleItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlashSaleItemInternalApiImpl implements FlashSaleItemInternalApi {

    private final FlashSaleItemRepository itemRepo;

    @Override
    @Transactional(readOnly = true)
    public FlashSaleItemBasicDto getActiveFlashSaleInfo(Integer productId, Integer variantId) {
        return itemRepo.findActiveSaleForProduct(productId, variantId)
                .filter(item -> !item.isSoldOut())
                .map(item -> {
                    FlashSaleItemBasicDto dto = new FlashSaleItemBasicDto();
                    dto.setFlashSaleId(item.getFlashSaleId());
                    dto.setFlashSaleItemId(item.getId());
                    dto.setPromotionalPrice(item.getPromotionalPrice());
                    return dto;
                }).orElse(null);
    }

    @Override
    @Transactional
    public boolean consumeFlashSaleStock(Long flashSaleItemId, int qty) {
        // Atomic Update
        int updatedRows = itemRepo.consumeStock(flashSaleItemId, qty);
        // NOTE: Nếu updatedRows > 0, ta có thể query lại để kiểm tra nếu soldCount == limit thì bắn Event SoldOut.
        return updatedRows > 0;
    }

    @Override
    @Transactional
    public void releaseFlashSaleStock(Long flashSaleItemId, int qty) {
        itemRepo.releaseStock(flashSaleItemId, qty);
    }
}