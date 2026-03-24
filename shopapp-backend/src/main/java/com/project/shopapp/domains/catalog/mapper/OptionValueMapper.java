package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.OptionValueCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionValueResponse;
import com.project.shopapp.domains.catalog.entity.OptionValue;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionValueMapper extends BaseMapper<OptionValueResponse, OptionValue> {
    OptionValue toEntityFromRequest(OptionValueCreateRequest request);
}