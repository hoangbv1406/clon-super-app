package com.project.shopapp.domains.sales.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.sales.dto.request.CartMergeRequest;
import com.project.shopapp.domains.sales.dto.response.CartResponse;
import com.project.shopapp.domains.sales.service.CartService;
import com.project.shopapp.domains.sales.validation.ValidSessionId;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales/carts")
@RequiredArgsConstructor
@Validated
public class CartController {

    private final CartService cartService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Xem giỏ hàng (Guest & User)
    @GetMapping("/mine")
    public ResponseEntity<ResponseObject<CartResponse>> getMyCart(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(cartService.getMyCart(userId, sessionId)));
    }

    // AUTH: Đồng bộ giỏ Guest vào giỏ User (Gọi tự động sau khi FE thực hiện Đăng nhập thành công)
    @PostMapping("/merge")
    public ResponseEntity<ResponseObject<Void>> mergeCart(
            @Valid @RequestBody CartMergeRequest request) {
        Integer userId = securityUtils.getLoggedInUserId(); // Bắt buộc user
        cartService.mergeGuestCartToUser(userId, request.getSessionId());
        return ResponseEntity.ok(ResponseObject.success(null, "Đồng bộ giỏ hàng thành công"));
    }

    // ADMIN: Theo dõi các giỏ hàng (Phục vụ CSKH)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<PageResponse<CartResponse>>> getCartsForAdmin(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean isGuest,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(cartService.getCartsForAdmin(status, isGuest, page, size)));
    }
}