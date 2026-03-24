// --- api/impl/FlashSaleInternalApiImpl.java ---
package com.project.shopapp.domains.marketing.api.impl;

import com.project.shopapp.domains.marketing.api.FlashSaleInternalApi;
import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import com.project.shopapp.domains.marketing.repository.FlashSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlashSaleInternalApiImpl implements FlashSaleInternalApi {
    private final FlashSaleRepository repo;

    @Override
    @Transactional(readOnly = true)
    public boolean isFlashSaleActive(Integer flashSaleId) {
        return repo.findById(flashSaleId)
                .map(f -> f.getStatus() == FlashSaleStatus.ACTIVE && f.getIsDeleted() == 0L)
                .orElse(false);
    }
}