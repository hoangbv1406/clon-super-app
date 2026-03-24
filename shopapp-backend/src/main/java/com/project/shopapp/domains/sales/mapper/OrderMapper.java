package com.project.shopapp.domains.sales.mapper;

import com.project.shopapp.domains.sales.dto.request.OrderCheckoutRequest;
import com.project.shopapp.domains.sales.dto.response.OrderResponse;
import com.project.shopapp.domains.sales.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toDto(Order entity);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    Order toEntityFromRequest(OrderCheckoutRequest request);
}