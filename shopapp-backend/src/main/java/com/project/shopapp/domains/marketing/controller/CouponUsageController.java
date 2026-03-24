package com.project.shopapp.domains.marketing.controller;

import com.project.shopapp.domains.marketing.dto.response.CouponUsageResponse;
import com.project.shopapp.domains.marketing.service.CouponUsageService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marketing/coupon-usages")
@RequiredArgsConstructor
public class CouponUsageController {

    private final CouponUsageService usageService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDOR')")
    public ResponseEntity<ResponseObject<PageResponse<CouponUsageResponse>>> getUsages(
            @RequestParam(required = false) Integer couponId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                usageService.getUsagesForAdmin(couponId, userId, status, page, size)
        ));
    }
}