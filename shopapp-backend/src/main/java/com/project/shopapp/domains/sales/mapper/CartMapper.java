package com.project.shopapp.domains.sales.mapper;

import com.project.shopapp.domains.sales.dto.response.CartResponse;
import com.project.shopapp.domains.sales.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalItems", ignore = true) // Computed at Service
    @Mapping(target = "totalPrice", ignore = true) // Computed at Service
    CartResponse toDto(Cart entity);
}