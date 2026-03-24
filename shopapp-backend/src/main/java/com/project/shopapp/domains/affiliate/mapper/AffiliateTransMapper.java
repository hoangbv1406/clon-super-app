package com.project.shopapp.domains.affiliate.mapper;

import com.project.shopapp.domains.affiliate.dto.response.AffiliateTransResponse;
import com.project.shopapp.domains.affiliate.entity.AffiliateTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AffiliateTransMapper {

    @Mapping(source = "affiliateLink.code", target = "affiliateCode")
    @Mapping(source = "orderShop.orderShopCode", target = "orderShopCode")
    AffiliateTransResponse toDto(AffiliateTransaction entity);
}