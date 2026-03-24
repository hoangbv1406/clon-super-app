package com.project.shopapp.domains.vendor.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.vendor.dto.request.ShopRegistrationRequest;
import com.project.shopapp.domains.vendor.dto.request.ShopStatusUpdateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopResponse;
import com.project.shopapp.domains.vendor.service.ShopService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Khách hàng xem profile Shop
    @GetMapping("/{slug}")
    public ResponseEntity<ResponseObject<ShopResponse>> getShopProfile(@PathVariable String slug) {
        return ResponseEntity.ok(ResponseObject.success(shopService.getShopBySlug(slug)));
    }

    // USER: Đăng ký trở thành nhà bán
    @PostMapping("/register")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<ShopResponse>> registerShop(@Valid @RequestBody ShopRegistrationRequest request) {
        Integer ownerId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                shopService.registerShop(ownerId, request), "Đăng ký mở gian hàng thành công. Vui lòng chờ Admin duyệt."
        ));
    }

    // ADMIN: Duyệt / Khóa Shop
    @PatchMapping("/{shopId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<ShopResponse>> updateShopStatus(
            @PathVariable Integer shopId,
            @Valid @RequestBody ShopStatusUpdateRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                shopService.updateShopStatus(adminId, shopId, request), "Cập nhật trạng thái gian hàng thành công"
        ));
    }
}