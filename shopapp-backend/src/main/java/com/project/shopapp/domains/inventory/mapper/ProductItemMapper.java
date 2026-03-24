package com.project.shopapp.domains.inventory.mapper;

import com.project.shopapp.domains.inventory.dto.response.ProductItemResponse;
import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductItemMapper extends BaseMapper<ProductItemResponse, ProductItem> {
}