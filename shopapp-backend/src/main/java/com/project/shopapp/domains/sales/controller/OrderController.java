package com.project.shopapp.domains.sales.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.sales.dto.request.OrderCheckoutRequest;
import com.project.shopapp.domains.sales.dto.response.OrderResponse;
import com.project.shopapp.domains.sales.service.OrderService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SecurityUtils securityUtils;

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<OrderResponse>> checkout(
            @Valid @RequestBody OrderCheckoutRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                orderService.checkout(userId, request), "Đặt hàng thành công"
        ));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<PageResponse<OrderResponse>>> getMyOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(orderService.getMyOrders(userId, page, size)));
    }

    @PatchMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<Void>> cancelMyOrder(@PathVariable Long orderId) {
        Integer userId = securityUtils.getLoggedInUserId();
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok(ResponseObject.success(null, "Hủy đơn hàng thành công"));
    }
}