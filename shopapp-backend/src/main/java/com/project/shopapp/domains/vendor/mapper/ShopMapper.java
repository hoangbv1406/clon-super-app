package com.project.shopapp.domains.vendor.mapper;

import com.project.shopapp.domains.vendor.dto.request.ShopRegistrationRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopResponse;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper extends BaseMapper<ShopResponse, Shop> {
    Shop toEntityFromRequest(ShopRegistrationRequest request);
}