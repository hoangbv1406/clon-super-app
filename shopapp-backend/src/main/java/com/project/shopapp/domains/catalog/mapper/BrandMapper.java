package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.BrandCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.BrandResponse;
import com.project.shopapp.domains.catalog.entity.Brand;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper extends BaseMapper<BrandResponse, Brand> {
    Brand toEntityFromRequest(BrandCreateRequest request);
}