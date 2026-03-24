package com.project.shopapp.domains.marketing.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.marketing.dto.request.CouponCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.CouponResponse;
import com.project.shopapp.domains.marketing.service.CouponService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marketing/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Lấy danh sách Voucher khả dụng của 1 Shop (và của Sàn) để render ra màn hình giỏ hàng
    @GetMapping("/available")
    public ResponseEntity<ResponseObject<List<CouponResponse>>> getAvailable(
            @RequestParam(required = false) Integer shopId) {
        return ResponseEntity.ok(ResponseObject.success(couponService.getAvailableCouponsForShop(shopId)));
    }

    // ADMIN/VENDOR: Tạo mã mới
    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<CouponResponse>> createCoupon(
            @RequestParam(required = false) Integer shopId, // Vendor truyền shopId, Admin không truyền
            @Valid @RequestBody CouponCreateRequest request) {

        Integer userId = securityUtils.getLoggedInUserId();
        // TODO: Cần Security check: Vendor chỉ được truyền shopId của chính mình.

        return ResponseEntity.ok(ResponseObject.created(
                couponService.createCoupon(userId, shopId, request), "Tạo mã giảm giá thành công"
        ));
    }

    // ADMIN/VENDOR: Khóa mã
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<Void>> toggleStatus(
            @PathVariable Integer id,
            @RequestParam boolean isActive) {

        Integer userId = securityUtils.getLoggedInUserId();
        couponService.toggleCouponStatus(userId, id, isActive);
        return ResponseEntity.ok(ResponseObject.success(null, "Cập nhật trạng thái thành công"));
    }
}