package com.project.shopapp.domains.after_sales.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.after_sales.dto.request.WarrantyCreateRequest;
import com.project.shopapp.domains.after_sales.dto.request.WarrantyProcessRequest;
import com.project.shopapp.domains.after_sales.dto.response.WarrantyResponse;
import com.project.shopapp.domains.after_sales.service.WarrantyService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WarrantyController {

    private final WarrantyService warrantyService;
    private final SecurityUtils securityUtils;

    // USER: Khách tạo khiếu nại
    @PostMapping("/after-sales/requests")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<WarrantyResponse>> createRequest(@Valid @RequestBody WarrantyCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                warrantyService.createRequest(userId, request), "Đã tạo phiếu Yêu cầu Hậu mãi"
        ));
    }

    // USER: Khách cập nhật mã vận đơn trả hàng
    @PatchMapping("/after-sales/requests/{id}/tracking")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<Void>> updateTracking(
            @PathVariable Integer id,
            @RequestParam String trackingCode) {
        Integer userId = securityUtils.getLoggedInUserId();
        warrantyService.updateReturnTracking(userId, id, trackingCode);
        return ResponseEntity.ok(ResponseObject.success(null, "Cập nhật mã vận đơn thành công"));
    }

    // USER/VENDOR: Xem danh sách phiếu
    @GetMapping("/after-sales/requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<PageResponse<WarrantyResponse>>> searchRequests(
            @RequestParam(required = false) Integer shopId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        // Tùy theo Role mà truyền tham số ẩn. Nếu là USER thì chỉ truyền userId. Nếu là VENDOR thì truyền shopId.
        Integer userId = securityUtils.isUser() ? securityUtils.getLoggedInUserId() : null;
        Integer filterShopId = securityUtils.isVendor() ? shopId : null; // Vendor tự truyền shopId của mình

        return ResponseEntity.ok(ResponseObject.success(
                warrantyService.searchRequests(filterShopId, userId, status, code, page, size)
        ));
    }

    // VENDOR: Xử lý phiếu khiếu nại
    @PatchMapping("/vendor/shops/{shopId}/rma/{requestId}/process")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'CSKH')")
    public ResponseEntity<ResponseObject<WarrantyResponse>> processRequest(
            @PathVariable Integer shopId,
            @PathVariable Integer requestId,
            @Valid @RequestBody WarrantyProcessRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                warrantyService.processRequest(adminId, shopId, requestId, request), "Đã cập nhật trạng thái phiếu"
        ));
    }
}