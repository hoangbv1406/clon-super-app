package com.project.shopapp.domains.sales.mapper;

import com.project.shopapp.domains.sales.dto.response.OrderShopResponse;
import com.project.shopapp.domains.sales.entity.OrderShop;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderShopMapper extends BaseMapper<OrderShopResponse, OrderShop> {
}