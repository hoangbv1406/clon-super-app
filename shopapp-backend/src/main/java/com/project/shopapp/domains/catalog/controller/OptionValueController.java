package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.request.OptionValueCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionValueResponse;
import com.project.shopapp.domains.catalog.service.OptionValueService;
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
@RequestMapping("/api/v1/catalog") // Chú ý cấu trúc URL gộp chung
@RequiredArgsConstructor
public class OptionValueController {

    private final OptionValueService valueService;
    private final SecurityUtils securityUtils;

    // PUBLIC / VENDOR: Lấy giá trị của một Option (VD: Lấy tất cả Màu Sắc)
    @GetMapping("/options/{optionId}/values")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<OptionValueResponse>>> getValuesByOption(@PathVariable Integer optionId) {
        return ResponseEntity.ok(ResponseObject.success(valueService.getActiveValuesByOption(optionId)));
    }

    // ADMIN: Phân trang & Search
    @GetMapping("/options/{optionId}/values/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<PageResponse<OptionValueResponse>>> searchValues(
            @PathVariable Integer optionId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(valueService.getValuesForAdmin(optionId, keyword, page, size)));
    }

    // ADMIN: Tạo mới
    @PostMapping("/option-values")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<OptionValueResponse>> createOptionValue(
            @Valid @RequestBody OptionValueCreateRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                valueService.createOptionValue(adminId, request), "Thêm giá trị tùy chọn thành công"
        ));
    }

    // ADMIN: Xóa mềm
    @DeleteMapping("/option-values/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<Void>> deleteOptionValue(@PathVariable Integer id) {
        Integer adminId = securityUtils.getLoggedInUserId();
        valueService.deleteOptionValue(adminId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa giá trị tùy chọn"));
    }
}