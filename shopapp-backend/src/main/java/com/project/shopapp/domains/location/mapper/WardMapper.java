package com.project.shopapp.domains.location.mapper;

import com.project.shopapp.domains.location.dto.response.WardResponse;
import com.project.shopapp.domains.location.entity.Ward;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WardMapper extends BaseMapper<WardResponse, Ward> {
}