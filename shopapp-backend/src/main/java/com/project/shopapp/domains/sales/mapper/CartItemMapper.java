package com.project.shopapp.domains.sales.mapper;

import com.project.shopapp.domains.sales.dto.request.CartItemAddRequest;
import com.project.shopapp.domains.sales.dto.response.CartItemResponse;
import com.project.shopapp.domains.sales.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "variantName", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "currentPrice", ignore = true)
    @Mapping(target = "isPriceChanged", ignore = true)
    @Mapping(target = "isOutOfStock", ignore = true)
    @Mapping(target = "currentStock", ignore = true)
    @Mapping(target = "lineTotal", ignore = true)
    CartItemResponse toDto(CartItem entity);

    CartItem toEntityFromRequest(CartItemAddRequest request);
}