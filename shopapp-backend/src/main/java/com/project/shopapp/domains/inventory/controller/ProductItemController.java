package com.project.shopapp.domains.inventory.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.inventory.dto.request.ItemBatchCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.ProductItemResponse;
import com.project.shopapp.domains.inventory.service.ProductItemService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory/items")
@RequiredArgsConstructor
public class ProductItemController {

    private final ProductItemService itemService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<PageResponse<ProductItemResponse>>> searchItems(
            @RequestParam(required = false) Integer productId,
            @RequestParam(required = false) Integer variantId,
            @RequestParam(required = false) String imei,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(ResponseObject.success(
                itemService.searchItems(productId, variantId, imei, status, page, size)
        ));
    }

    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<List<ProductItemResponse>>> batchCreate(
            @Valid @RequestBody ItemBatchCreateRequest request) {

        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                itemService.batchCreateItems(adminId, request), "Nhập kho Serial/IMEI thành công"
        ));
    }

    @PatchMapping("/{itemId}/defective")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER')")
    public ResponseEntity<ResponseObject<ProductItemResponse>> markDefective(@PathVariable Integer itemId) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                itemService.markAsDefective(adminId, itemId), "Đã ghi nhận hàng lỗi"
        ));
    }
}