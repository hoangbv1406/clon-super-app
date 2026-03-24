package com.project.shopapp.domains.after_sales.mapper;

import com.project.shopapp.domains.after_sales.dto.request.WarrantyCreateRequest;
import com.project.shopapp.domains.after_sales.dto.response.WarrantyResponse;
import com.project.shopapp.domains.after_sales.entity.WarrantyRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarrantyMapper {
    WarrantyResponse toDto(WarrantyRequest entity);

    @Mapping(target = "requestType", ignore = true) // Map manually
    WarrantyRequest toEntityFromRequest(WarrantyCreateRequest request);
}