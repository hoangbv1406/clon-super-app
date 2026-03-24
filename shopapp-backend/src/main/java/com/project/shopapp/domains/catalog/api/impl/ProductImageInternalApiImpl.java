package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.ProductImageInternalApi;
import com.project.shopapp.domains.catalog.dto.nested.ProductImageBasicDto;
import com.project.shopapp.domains.catalog.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageInternalApiImpl implements ProductImageInternalApi {

    private final ProductImageRepository imageRepo;

    @Override
    @Transactional(readOnly = true)
    public List<ProductImageBasicDto> getGalleryByProductId(Integer productId) {
        return imageRepo.findByProductIdAndIsDeletedOrderByDisplayOrderAsc(productId, 0L)
                .stream().map(img -> {
                    ProductImageBasicDto dto = new ProductImageBasicDto();
                    dto.setImageUrl(img.getImageUrl());
                    dto.setDisplayOrder(img.getDisplayOrder());
                    return dto;
                }).collect(Collectors.toList());
    }
}