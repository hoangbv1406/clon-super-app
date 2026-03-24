package com.project.shopapp.domains.marketing.mapper;

import com.project.shopapp.domains.marketing.dto.request.FlashSaleItemCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleItemResponse;
import com.project.shopapp.domains.marketing.entity.FlashSaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlashSaleItemMapper {

    @Mapping(target = "isSoldOut", expression = "java(entity.isSoldOut())")
    @Mapping(target = "productName", ignore = true) // Set bằng Internal API ở Service
    @Mapping(target = "productImage", ignore = true)
    @Mapping(target = "originalPrice", ignore = true)
    FlashSaleItemResponse toDto(FlashSaleItem entity);

    FlashSaleItem toEntityFromRequest(FlashSaleItemCreateRequest request);
}