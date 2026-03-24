package com.project.shopapp.domains.sales.mapper;

import com.project.shopapp.domains.sales.dto.response.OrderHistoryResponse;
import com.project.shopapp.domains.sales.entity.OrderHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderHistoryMapper {

    @Mapping(source = "updater.fullName", target = "updaterName", defaultValue = "Hệ thống")
    OrderHistoryResponse toDto(OrderHistory entity);
}