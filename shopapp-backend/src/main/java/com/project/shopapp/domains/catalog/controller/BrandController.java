package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.request.BrandCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.BrandResponse;
import com.project.shopapp.domains.catalog.service.BrandService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix:/api/v1}/catalog/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    // PUBLIC: Cho phép mọi người (Kể cả chưa đăng nhập) xem danh sách thương hiệu để lọc sản phẩm
    @GetMapping
    public ResponseEntity<ResponseObject<PageResponse<BrandResponse>>> getAllBrands(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tier,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(ResponseObject.success(
                brandService.getAllBrands(keyword, tier, isActive, page, size)
        ));
    }

    // ADMIN: Tạo thương hiệu mới
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<BrandResponse>> createBrand(@Valid @RequestBody BrandCreateRequest request) {
        return ResponseEntity.ok(ResponseObject.created(
                brandService.createBrand(request), "Thêm thương hiệu mới thành công"
        ));
    }

    // ADMIN: Khóa/Mở khóa thương hiệu
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<BrandResponse>> updateBrandStatus(
            @PathVariable Integer id,
            @RequestParam boolean isActive) {
        return ResponseEntity.ok(ResponseObject.success(
                brandService.updateBrandStatus(id, isActive), "Cập nhật trạng thái thành công"
        ));
    }

    // ADMIN: Xóa mềm
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<Void>> deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa thương hiệu"));
    }
}