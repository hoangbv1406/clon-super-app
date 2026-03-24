package com.project.shopapp.domains.marketing.mapper;

import com.project.shopapp.domains.marketing.dto.request.ApplicableCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.ApplicableResponse;
import com.project.shopapp.domains.marketing.entity.CouponApplicable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicableMapper {

    @Mapping(target = "objectName", ignore = true) // Sẽ map thủ công ở Service
    ApplicableResponse toDto(CouponApplicable entity);

    CouponApplicable toEntityFromRequest(ApplicableCreateRequest request);
}