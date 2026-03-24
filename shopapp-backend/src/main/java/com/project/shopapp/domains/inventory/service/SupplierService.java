package com.project.shopapp.domains.inventory.service;
import com.project.shopapp.domains.inventory.dto.request.SupplierCreateRequest;
import com.project.shopapp.domains.inventory.dto.request.SupplierUpdateRequest;
import com.project.shopapp.domains.inventory.dto.response.SupplierResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface SupplierService {
    PageResponse<SupplierResponse> searchSuppliers(Integer shopId, String keyword, String status, int page, int size);
    SupplierResponse createSupplier(Integer currentUserId, Integer shopId, SupplierCreateRequest request);
    SupplierResponse updateSupplier(Integer currentUserId, Integer shopId, Integer supplierId, SupplierUpdateRequest request);
    void deleteSupplier(Integer currentUserId, Integer shopId, Integer supplierId);
}