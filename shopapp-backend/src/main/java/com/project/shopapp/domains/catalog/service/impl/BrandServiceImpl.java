package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.request.BrandCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.BrandResponse;
import com.project.shopapp.domains.catalog.entity.Brand;
import com.project.shopapp.domains.catalog.enums.BrandTier;
import com.project.shopapp.domains.catalog.event.BrandStatusChangedEvent;
import com.project.shopapp.domains.catalog.mapper.BrandMapper;
import com.project.shopapp.domains.catalog.repository.BrandRepository;
import com.project.shopapp.domains.catalog.service.BrandService;
import com.project.shopapp.domains.catalog.specification.BrandSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "brands_list", key = "#keyword + '_' + #tier + '_' + #isActive + '_' + #page + '_' + #size")
    public PageResponse<BrandResponse> getAllBrands(String keyword, String tier, Boolean isActive, int page, int size) {
        BrandTier brandTier = (tier != null && !tier.isBlank()) ? BrandTier.valueOf(tier.toUpperCase()) : null;

        Page<Brand> brandPage = brandRepository.findAll(
                BrandSpecification.filter(keyword, brandTier, isActive),
                PageRequest.of(page - 1, size, Sort.by("name").ascending())
        );

        return PageResponse.of(brandPage.map(brandMapper::toDto));
    }

    @Override
    @Transactional
    @CacheEvict(value = "brands_list", allEntries = true)
    public BrandResponse createBrand(BrandCreateRequest request) {
        Brand brand = brandMapper.toEntityFromRequest(request);

        // Auto-generate Slug (VD: "Apple Inc" -> "apple-inc")
        String baseSlug = SlugUtils.toSlug(request.getName());
        String finalSlug = baseSlug;
        int counter = 1;
        while (brandRepository.existsBySlugAndIsDeleted(finalSlug, 0L)) {
            finalSlug = baseSlug + "-" + counter++;
        }
        brand.setSlug(finalSlug);

        return brandMapper.toDto(brandRepository.save(brand));
    }

    @Override
    @Transactional
    @CacheEvict(value = "brands_list", allEntries = true)
    public BrandResponse updateBrandStatus(Integer id, boolean isActive) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Thương hiệu không tồn tại"));

        if (brand.getIsActive() != isActive) {
            brand.setIsActive(isActive);
            brandRepository.save(brand);
            // Bắn event để ẩn/hiện sản phẩm
            eventPublisher.publishEvent(new BrandStatusChangedEvent(id, isActive));
        }

        return brandMapper.toDto(brand);
    }

    @Override
    @Transactional
    @CacheEvict(value = "brands_list", allEntries = true)
    public void deleteBrand(Integer id) {
        Brand brand = brandRepository.findById(id).orElseThrow();
        // Soft delete bằng ID thay vì boolean để nhả unique constraint của slug ra
        brand.setIsDeleted(System.currentTimeMillis());
        brand.setIsActive(false);
        brandRepository.save(brand);

        eventPublisher.publishEvent(new BrandStatusChangedEvent(id, false));
    }
}
