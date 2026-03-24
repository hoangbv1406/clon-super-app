package com.project.shopapp.domains.marketing.mapper;

import com.project.shopapp.domains.marketing.dto.request.FlashSaleCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleResponse;
import com.project.shopapp.domains.marketing.entity.FlashSale;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlashSaleMapper extends BaseMapper<FlashSaleResponse, FlashSale> {
    FlashSale toEntityFromRequest(FlashSaleCreateRequest request);
}