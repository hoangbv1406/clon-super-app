package com.project.shopapp.domains.inventory.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.inventory.dto.request.SupplierCreateRequest;
import com.project.shopapp.domains.inventory.dto.request.SupplierUpdateRequest;
import com.project.shopapp.domains.inventory.dto.response.SupplierResponse;
import com.project.shopapp.domains.inventory.service.SupplierService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/shops/{shopId}/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<PageResponse<SupplierResponse>>> searchSuppliers(
            @PathVariable Integer shopId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                supplierService.searchSuppliers(shopId, keyword, status, page, size)
        ));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER')")
    public ResponseEntity<ResponseObject<SupplierResponse>> createSupplier(
            @PathVariable Integer shopId,
            @Valid @RequestBody SupplierCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                supplierService.createSupplier(userId, shopId, request), "Thêm Nhà cung cấp thành công"
        ));
    }

    @PutMapping("/{supplierId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER')")
    public ResponseEntity<ResponseObject<SupplierResponse>> updateSupplier(
            @PathVariable Integer shopId,
            @PathVariable Integer supplierId,
            @Valid @RequestBody SupplierUpdateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                supplierService.updateSupplier(userId, shopId, supplierId, request), "Cập nhật thành công"
        ));
    }

    @DeleteMapping("/{supplierId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER')")
    public ResponseEntity<ResponseObject<Void>> deleteSupplier(
            @PathVariable Integer shopId,
            @PathVariable Integer supplierId) {
        Integer userId = securityUtils.getLoggedInUserId();
        supplierService.deleteSupplier(userId, shopId, supplierId);
        return ResponseEntity.ok(ResponseObject.success(null, "Xóa Nhà cung cấp thành công"));
    }
}