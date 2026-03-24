package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.request.ProductCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductResponse;
import com.project.shopapp.domains.catalog.service.ProductService;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SecurityUtils securityUtils;

    @GetMapping
    public ResponseEntity<ResponseObject<PageResponse<ProductResponse>>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) String ram,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(ResponseObject.success(
                productService.searchProducts(keyword, categoryId, brandId, ram, page, size)
        ));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ResponseObject<ProductResponse>> getProduct(@PathVariable String slug) {
        return ResponseEntity.ok(ResponseObject.success(productService.getProductBySlug(slug)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<ProductResponse>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                productService.createProduct(userId, request), "Thêm sản phẩm thành công"
        ));
    }
}