package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.request.ProductVariantCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductVariantResponse;
import com.project.shopapp.domains.catalog.service.ProductVariantService;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService variantService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Xem danh sách biến thể của 1 sản phẩm
    @GetMapping("/products/{productId}/variants")
    public ResponseEntity<ResponseObject<List<ProductVariantResponse>>> getVariants(@PathVariable Integer productId) {
        return ResponseEntity.ok(ResponseObject.success(variantService.getVariantsOfProduct(productId)));
    }

    // VENDOR / ADMIN: Tạo biến thể mới cho sản phẩm
    @PostMapping("/variants")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<ProductVariantResponse>> createVariant(@Valid @RequestBody ProductVariantCreateRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                variantService.createVariant(adminId, request), "Thêm cấu hình sản phẩm thành công"
        ));
    }
}