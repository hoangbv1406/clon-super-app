package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.request.OptionCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionResponse;
import com.project.shopapp.domains.catalog.service.OptionService;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    private final SecurityUtils securityUtils;

    // PUBLIC / VENDOR: Lấy danh sách master data để render UI tạo Sản phẩm
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<OptionResponse>>> getActiveOptions() {
        return ResponseEntity.ok(ResponseObject.success(optionService.getAllActiveOptions()));
    }

    // ADMIN: Xem và quản lý
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<PageResponse<OptionResponse>>> searchOptions(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(optionService.searchOptions(keyword, page, size)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<OptionResponse>> createOption(@Valid @RequestBody OptionCreateRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                optionService.createOption(adminId, request), "Thêm Tùy chọn hệ thống thành công"
        ));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<OptionResponse>> toggleStatus(
            @PathVariable Integer id,
            @RequestParam boolean isActive) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                optionService.toggleOptionStatus(adminId, id, isActive), "Cập nhật trạng thái thành công"
        ));
    }
}