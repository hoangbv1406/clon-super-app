// --- service/WarrantyService.java ---
package com.project.shopapp.domains.after_sales.service;
import com.project.shopapp.domains.after_sales.dto.request.WarrantyCreateRequest;
import com.project.shopapp.domains.after_sales.dto.request.WarrantyProcessRequest;
import com.project.shopapp.domains.after_sales.dto.response.WarrantyResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface WarrantyService {
    WarrantyResponse createRequest(Integer userId, WarrantyCreateRequest request);
    PageResponse<WarrantyResponse> searchRequests(Integer shopId, Integer userId, String status, String code, int page, int size);
    WarrantyResponse processRequest(Integer adminId, Integer shopId, Integer requestId, WarrantyProcessRequest request);
    void updateReturnTracking(Integer userId, Integer requestId, String trackingCode);
}