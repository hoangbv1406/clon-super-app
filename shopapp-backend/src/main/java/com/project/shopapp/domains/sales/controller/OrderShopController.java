package com.project.shopapp.domains.sales.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.sales.dto.request.OrderShopFulfillmentRequest;
import com.project.shopapp.domains.sales.dto.response.OrderShopResponse;
import com.project.shopapp.domains.sales.service.OrderShopService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/shops/{shopId}/sub-orders")
@RequiredArgsConstructor
public class OrderShopController {

    private final OrderShopService orderShopService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'SALES')")
    public ResponseEntity<ResponseObject<PageResponse<OrderShopResponse>>> getOrders(
            @PathVariable Integer shopId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String code,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        // TODO: Chặn IDOR - Gọi EmployeeApi để check quyền của current userId tại shopId này
        return ResponseEntity.ok(ResponseObject.success(orderShopService.getVendorOrders(shopId, status, code, page, size)));
    }

    @PatchMapping("/{orderShopId}/fulfillment")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'SALES')")
    public ResponseEntity<ResponseObject<OrderShopResponse>> updateFulfillment(
            @PathVariable Integer shopId,
            @PathVariable Integer orderShopId,
            @Valid @RequestBody OrderShopFulfillmentRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                orderShopService.updateFulfillment(userId, shopId, orderShopId, request), "Cập nhật vận đơn thành công"
        ));
    }

    @PatchMapping("/{orderShopId}/ship")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<Void>> markAsShipped(
            @PathVariable Integer shopId,
            @PathVariable Integer orderShopId) {
        Integer userId = securityUtils.getLoggedInUserId();
        orderShopService.markAsShipped(userId, shopId, orderShopId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã chuyển trạng thái Đang giao hàng"));
    }
}