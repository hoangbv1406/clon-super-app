package com.project.shopapp.domains.location.mapper;

import com.project.shopapp.domains.location.dto.request.DistrictCreateRequest;
import com.project.shopapp.domains.location.dto.response.DistrictResponse;
import com.project.shopapp.domains.location.entity.District;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper extends BaseMapper<DistrictResponse, District> {
    District toEntityFromCreateRequest(DistrictCreateRequest request);
}