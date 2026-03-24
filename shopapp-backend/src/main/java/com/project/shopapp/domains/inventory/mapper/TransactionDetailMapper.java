package com.project.shopapp.domains.inventory.mapper;

import com.project.shopapp.domains.inventory.dto.request.TransactionDetailCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionDetailResponse;
import com.project.shopapp.domains.inventory.entity.InventoryTransactionDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionDetailMapper {

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "variant.name", target = "variantName")
    @Mapping(target = "lineTotal", expression = "java(entity.getUnitCost().multiply(new java.math.BigDecimal(Math.abs(entity.getQuantityChanged()))))")
    TransactionDetailResponse toDto(InventoryTransactionDetail entity);

    @Mapping(target = "stockBefore", ignore = true) // Backend sẽ tự tính lúc duyệt
    @Mapping(target = "stockAfter", ignore = true)  // Backend sẽ tự tính lúc duyệt
    InventoryTransactionDetail toEntityFromRequest(TransactionDetailCreateRequest request);
}