package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.ProductImageCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductImageResponse;
import com.project.shopapp.domains.catalog.entity.ProductImage;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper extends BaseMapper<ProductImageResponse, ProductImage> {
    ProductImage toEntityFromRequest(ProductImageCreateRequest request);
}