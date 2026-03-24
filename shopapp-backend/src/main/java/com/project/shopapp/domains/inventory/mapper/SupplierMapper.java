package com.project.shopapp.domains.inventory.mapper;

import com.project.shopapp.domains.inventory.dto.request.SupplierCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.SupplierResponse;
import com.project.shopapp.domains.inventory.entity.Supplier;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper extends BaseMapper<SupplierResponse, Supplier> {
    Supplier toEntityFromRequest(SupplierCreateRequest request);
}