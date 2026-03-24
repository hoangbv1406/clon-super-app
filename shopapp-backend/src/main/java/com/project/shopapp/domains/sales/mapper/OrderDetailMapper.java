package com.project.shopapp.domains.sales.mapper;

import com.project.shopapp.domains.sales.dto.response.OrderDetailResponse;
import com.project.shopapp.domains.sales.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse toDto(OrderDetail entity);
}