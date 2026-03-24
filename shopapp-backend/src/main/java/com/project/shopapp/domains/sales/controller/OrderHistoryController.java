package com.project.shopapp.domains.sales.controller;

import com.project.shopapp.domains.sales.dto.response.OrderHistoryResponse;
import com.project.shopapp.domains.sales.service.OrderHistoryService;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderHistoryService historyService;

    // PUBLIC / USER: Xem hành trình đơn hàng tổng
    @GetMapping("/sales/orders/{orderId}/timeline")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<List<OrderHistoryResponse>>> getOrderTimeline(
            @PathVariable Long orderId) {
        // TODO: Chặn IDOR (Đảm bảo orderId thuộc về currentUser) ở tầng Filter hoặc Service
        return ResponseEntity.ok(ResponseObject.success(historyService.getOrderTimeline(orderId)));
    }

    // VENDOR / USER: Xem hành trình kiện hàng con
    @GetMapping("/sales/sub-orders/{orderShopId}/timeline")
    @PreAuthorize("isAuthenticated()") // User mua hàng hoặc Vendor bán hàng đều xem được
    public ResponseEntity<ResponseObject<List<OrderHistoryResponse>>> getOrderShopTimeline(
            @PathVariable Integer orderShopId) {
        return ResponseEntity.ok(ResponseObject.success(historyService.getOrderShopTimeline(orderShopId)));
    }
}