package com.project.shopapp.domains.marketing.mapper;

import com.project.shopapp.domains.marketing.dto.response.CouponUsageResponse;
import com.project.shopapp.domains.marketing.entity.CouponUsage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponUsageMapper {

    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "coupon.code", target = "couponCode")
    CouponUsageResponse toDto(CouponUsage entity);
}