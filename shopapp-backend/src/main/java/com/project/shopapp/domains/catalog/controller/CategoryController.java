package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.request.CategoryCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.CategoryResponse;
import com.project.shopapp.domains.catalog.service.CategoryService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix:/api/v1}/catalog/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // PUBLIC: Frontend load menu trang chủ
    @GetMapping("/tree")
    public ResponseEntity<ResponseObject<List<CategoryResponse>>> getCategoryTree() {
        return ResponseEntity.ok(ResponseObject.success(categoryService.getCategoryTree()));
    }

    // ADMIN: Thêm danh mục mới
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<CategoryResponse>> createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        return ResponseEntity.ok(ResponseObject.created(
                categoryService.createCategory(request), "Tạo danh mục thành công"
        ));
    }

    // ADMIN: Xóa danh mục
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<Void>> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa danh mục"));
    }
}