package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.OptionCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionResponse;
import com.project.shopapp.domains.catalog.entity.Option;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionMapper extends BaseMapper<OptionResponse, Option> {
    Option toEntityFromRequest(OptionCreateRequest request);
}