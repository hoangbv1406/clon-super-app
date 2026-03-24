package com.project.shopapp.domains.sales.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.sales.dto.request.CartItemAddRequest;
import com.project.shopapp.domains.sales.dto.request.CartItemUpdateRequest;
import com.project.shopapp.domains.sales.dto.response.CartResponse; // Thay vì List, trả luôn Full Cart
import com.project.shopapp.domains.sales.service.CartItemService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales/carts/items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final SecurityUtils securityUtils;

    // Trả về toàn bộ Giỏ hàng (Cả Header + List Items đã được mix giá mới nhất)
    @GetMapping
    public ResponseEntity<ResponseObject<CartResponse>> getFullCartDetails(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(cartItemService.getFullCart(userId, sessionId)));
    }

    @PostMapping
    public ResponseEntity<ResponseObject<Void>> addItem(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @Valid @RequestBody CartItemAddRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        cartItemService.addItem(userId, sessionId, request);
        return ResponseEntity.ok(ResponseObject.created(null, "Đã thêm vào giỏ"));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ResponseObject<Void>> updateItem(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @PathVariable Integer itemId,
            @Valid @RequestBody CartItemUpdateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        cartItemService.updateItem(userId, sessionId, itemId, request);
        return ResponseEntity.ok(ResponseObject.success(null, "Cập nhật thành công"));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ResponseObject<Void>> removeItem(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @PathVariable Integer itemId) {
        Integer userId = securityUtils.getLoggedInUserId();
        cartItemService.removeItem(userId, sessionId, itemId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa khỏi giỏ"));
    }

    @PatchMapping("/toggle-all")
    public ResponseEntity<ResponseObject<Void>> toggleSelectAll(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestParam boolean isSelected) {
        Integer userId = securityUtils.getLoggedInUserId();
        cartItemService.toggleSelectAll(userId, sessionId, isSelected);
        return ResponseEntity.ok(ResponseObject.success(null, "Cập nhật chọn thành công"));
    }
}