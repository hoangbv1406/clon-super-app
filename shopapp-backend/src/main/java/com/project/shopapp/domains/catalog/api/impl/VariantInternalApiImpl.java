package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.VariantInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductVariantBasicDto;
import com.project.shopapp.domains.catalog.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VariantInternalApiImpl implements VariantInternalApi {
    private final ProductVariantRepository variantRepo;

    @Override
    @Transactional(readOnly = true)
    public ProductVariantBasicDto getVariantBasicInfo(Integer variantId) {
        return variantRepo.findById(variantId).filter(v -> v.getIsDeleted() == 0L).map(v -> {
            ProductVariantBasicDto dto = new ProductVariantBasicDto();
            dto.setId(v.getId());
            dto.setSku(v.getSku());
            dto.setName(v.getName()); // Giá trị từ DB sinh ra
            dto.setPrice(v.getPrice());
            dto.setImageUrl(v.getImageUrl());
            return dto;
        }).orElse(null);
    }

    @Override
    @Transactional
    public boolean lockStock(Integer variantId, Integer qty) {
        return variantRepo.lockVariantStock(variantId, qty) > 0;
    }

    @Override
    @Transactional
    public void unlockStock(Integer variantId, Integer qty) {
        variantRepo.unlockVariantStock(variantId, qty);
    }

    @Override
    @Transactional
    public void commitStock(Integer variantId, Integer qty) {
        variantRepo.commitVariantStock(variantId, qty);
    }
}