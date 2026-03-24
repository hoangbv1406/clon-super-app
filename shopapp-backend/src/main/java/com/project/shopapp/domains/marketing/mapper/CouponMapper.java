package com.project.shopapp.domains.marketing.mapper;

import com.project.shopapp.domains.marketing.dto.request.CouponCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.CouponResponse;
import com.project.shopapp.domains.marketing.entity.Coupon;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper extends BaseMapper<CouponResponse, Coupon> {

    @Mapping(target = "isExpired", expression = "java(entity.isExpired())")
    CouponResponse toDto(Coupon entity);

    Coupon toEntityFromRequest(CouponCreateRequest request);
}