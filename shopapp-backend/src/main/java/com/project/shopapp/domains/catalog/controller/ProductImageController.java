package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.response.ProductImageResponse;
import com.project.shopapp.domains.catalog.service.ProductImageService;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService imageService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Xem ảnh của SP (Được gọi cùng lúc khi fetch Product Detail)
    @GetMapping("/products/{productId}/images")
    public ResponseEntity<ResponseObject<List<ProductImageResponse>>> getProductImages(@PathVariable Integer productId) {
        return ResponseEntity.ok(ResponseObject.success(imageService.getImagesByProductId(productId)));
    }

    // VENDOR / ADMIN: Upload nhiều ảnh cùng lúc
    @PostMapping(value = "/products/{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<ProductImageResponse>>> uploadImages(
            @PathVariable Integer productId,
            @RequestParam("files") List<MultipartFile> files) {

        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                imageService.uploadImages(userId, productId, files), "Đăng tải hình ảnh thành công"
        ));
    }

    // VENDOR / ADMIN: Xóa ảnh
    @DeleteMapping("/images/{imageId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<Void>> deleteImage(@PathVariable Integer imageId) {
        Integer userId = securityUtils.getLoggedInUserId();
        imageService.deleteImage(userId, imageId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa ảnh thành công"));
    }
}