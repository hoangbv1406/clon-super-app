package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.BrandInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.BrandBasicDto;
import com.project.shopapp.domains.catalog.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandInternalApiImpl implements BrandInternalApi {

    private final BrandRepository brandRepo;

    @Override
    @Transactional(readOnly = true)
    public boolean isBrandActiveAndValid(Integer brandId) {
        return brandRepo.findById(brandId)
                .map(b -> b.getIsActive() && b.getIsDeleted() == 0L)
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandBasicDto getBrandBasicInfo(Integer brandId) {
        return brandRepo.findById(brandId)
                .filter(b -> b.getIsDeleted() == 0L)
                .map(b -> {
                    BrandBasicDto dto = new BrandBasicDto();
                    dto.setId(b.getId());
                    dto.setName(b.getName());
                    dto.setSlug(b.getSlug());
                    dto.setIconUrl(b.getIconUrl());
                    dto.setTier(b.getTier().name());
                    return dto;
                }).orElse(null);
    }
}