package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.VariantValueSyncRequest;
import com.project.shopapp.domains.catalog.entity.VariantValue;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariantValueMapper extends BaseMapper<VariantValueSyncRequest, VariantValue> {
//    VariantValue toEntityFromSyncRequest(VariantValueSyncRequest request);
}