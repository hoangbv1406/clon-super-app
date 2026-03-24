package com.project.shopapp.domains.marketing.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.marketing.dto.request.FlashSaleCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.FlashSaleResponse;
import com.project.shopapp.domains.marketing.service.FlashSaleService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1") // Tách Admin và Vendor qua role
@RequiredArgsConstructor
public class FlashSaleController {

    private final FlashSaleService flashSaleService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Lấy FS của Sàn (shopId = null) hoặc của Shop
    @GetMapping("/flash-sales")
    public ResponseEntity<ResponseObject<PageResponse<FlashSaleResponse>>> getFlashSales(
            @RequestParam(required = false) Integer shopId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(flashSaleService.searchSales(shopId, status, page, size)));
    }

    // VENDOR / ADMIN: Tạo FS
    @PostMapping("/vendor/shops/{shopId}/flash-sales")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<FlashSaleResponse>> createFlashSale(
            @PathVariable(required = false) Integer shopId,
            @Valid @RequestBody FlashSaleCreateRequest request) {

        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                flashSaleService.createFlashSale(userId, shopId, request), "Tạo chiến dịch Flash Sale thành công"
        ));
    }

    // VENDOR / ADMIN: Hủy
    @PatchMapping("/vendor/flash-sales/{id}/cancel")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<Void>> cancelFlashSale(@PathVariable Integer id) {
        Integer userId = securityUtils.getLoggedInUserId();
        flashSaleService.cancelFlashSale(userId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã hủy chiến dịch Flash Sale"));
    }
}