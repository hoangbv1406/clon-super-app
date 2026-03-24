package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.response.VariantMatrixResponse;
import com.project.shopapp.domains.catalog.service.VariantValueService;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/products/{productId}/variant-matrix")
@RequiredArgsConstructor
public class VariantValueController {

    private final VariantValueService matrixService;

    // ADMIN: Xem ma trận mapping để Debug hệ thống
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<List<VariantMatrixResponse>>> getProductMatrix(@PathVariable Integer productId) {
        return ResponseEntity.ok(ResponseObject.success(
                matrixService.getMatrixByProduct(productId),
                "Lấy ma trận biến thể thành công"
        ));
    }
}