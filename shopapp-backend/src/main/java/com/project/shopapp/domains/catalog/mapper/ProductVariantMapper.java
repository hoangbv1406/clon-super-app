package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.ProductVariantCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductVariantResponse;
import com.project.shopapp.domains.catalog.entity.ProductVariant;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper extends BaseMapper<ProductVariantResponse, ProductVariant> {

    @Mapping(target = "availableStock", expression = "java(entity.getAvailableStock())")
    ProductVariantResponse toDto(ProductVariant entity);

    @Mapping(target = "attributes", ignore = true) // Sẽ được build ở Service thông qua OptionValueInternalApi
    ProductVariant toEntityFromRequest(ProductVariantCreateRequest request);
}