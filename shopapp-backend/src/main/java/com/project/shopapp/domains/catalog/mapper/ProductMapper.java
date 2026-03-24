package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.ProductCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductResponse;
import com.project.shopapp.domains.catalog.entity.Product;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<ProductResponse, Product> {

    // Map available quantity từ hàm tiện ích trong Entity
    @Mapping(target = "availableQuantity", expression = "java(entity.getAvailableQuantity())")
    // Shop, Brand, Category sẽ được gán tự động nếu FetchType là EAGER,
    // Nếu LAZY, ta cần cẩn thận để Service fetch trước khi map, MapStruct sẽ tự ánh xạ.
    ProductResponse toDto(Product entity);

    Product toEntityFromRequest(ProductCreateRequest request);
}