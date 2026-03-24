package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.CategoryCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.CategoryResponse;
import com.project.shopapp.domains.catalog.entity.Category;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryResponse, Category> {

    @Mapping(target = "children", ignore = true) // Cấm MapStruct tự động map children để tránh N+1, Service sẽ tự xử lý
    CategoryResponse toDto(Category entity);

    Category toEntityFromRequest(CategoryCreateRequest request);
}