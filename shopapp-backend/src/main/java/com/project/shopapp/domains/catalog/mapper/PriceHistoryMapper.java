package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.response.PriceHistoryResponse;
import com.project.shopapp.domains.catalog.entity.PriceHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceHistoryMapper {

    @Mapping(source = "updater.fullName", target = "updaterName")
    PriceHistoryResponse toDto(PriceHistory entity);
}