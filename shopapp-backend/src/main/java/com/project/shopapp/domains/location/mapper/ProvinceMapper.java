package com.project.shopapp.domains.location.mapper;

import com.project.shopapp.domains.location.dto.request.ProvinceCreateRequest;
import com.project.shopapp.domains.location.dto.response.ProvinceResponse;
import com.project.shopapp.domains.location.entity.Province;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends BaseMapper<ProvinceResponse, Province> {
    Province toEntityFromCreateRequest(ProvinceCreateRequest request);
}