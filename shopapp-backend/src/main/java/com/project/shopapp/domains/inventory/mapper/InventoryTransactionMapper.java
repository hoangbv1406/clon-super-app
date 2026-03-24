package com.project.shopapp.domains.inventory.mapper;

import com.project.shopapp.domains.inventory.dto.request.TransactionCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionResponse;
import com.project.shopapp.domains.inventory.entity.InventoryTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryTransactionMapper {

    @Mapping(source = "creator.fullName", target = "creatorName")
    TransactionResponse toDto(InventoryTransaction entity);

    InventoryTransaction toEntityFromRequest(TransactionCreateRequest request);
}