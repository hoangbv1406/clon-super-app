package com.project.shopapp.domains.inventory.api;
import com.project.shopapp.domains.inventory.dto.nested.SupplierBasicDto;

public interface SupplierInternalApi {
    SupplierBasicDto getActiveSupplierForTransaction(Integer shopId, Integer supplierId);
}