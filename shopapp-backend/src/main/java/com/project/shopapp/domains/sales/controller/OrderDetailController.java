package com.project.shopapp.domains.sales.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.sales.dto.response.OrderDetailResponse;
import com.project.shopapp.domains.sales.service.OrderDetailService;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService detailService;
    private final SecurityUtils securityUtils;

    // PUBLIC/USER: Lấy chi tiết các món trong 1 Order bự
    @GetMapping("/sales/orders/{orderId}/details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<List<OrderDetailResponse>>> getOrderDetails(
            @PathVariable Long orderId) {
        // IDOR check được xử lý ngầm ở Service
        return ResponseEntity.ok(ResponseObject.success(detailService.getDetailsByOrderId(orderId)));
    }

    // VENDOR/ADMIN: Lấy chi tiết các món trong 1 Kiện hàng (OrderShop)
    @GetMapping("/vendor/sub-orders/{orderShopId}/details")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<OrderDetailResponse>>> getOrderShopDetails(
            @PathVariable Integer orderShopId) {
        return ResponseEntity.ok(ResponseObject.success(detailService.getDetailsByOrderShopId(orderShopId)));
    }

    // USER: Bấm nút "Trả hàng/Hoàn tiền" cho 1 mặt hàng cụ thể
    @PostMapping("/sales/order-details/{detailId}/return-request")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<Void>> requestPartialReturn(@PathVariable Integer detailId) {
        Integer userId = securityUtils.getLoggedInUserId();
        detailService.requestPartialReturn(userId, detailId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã gửi yêu cầu trả hàng cho mặt hàng này"));
    }
}