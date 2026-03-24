package com.project.shopapp.domains.vendor.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeCreateRequest;
import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeUpdateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopEmployeeResponse;
import com.project.shopapp.domains.vendor.service.ShopEmployeeService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor/shops/{shopId}/employees")
@RequiredArgsConstructor
public class ShopEmployeeController {

    private final ShopEmployeeService empService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<ShopEmployeeResponse>>> getEmployees(@PathVariable Integer shopId) {
        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(empService.getEmployees(currentUserId, shopId)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<ShopEmployeeResponse>> addEmployee(
            @PathVariable Integer shopId,
            @Valid @RequestBody ShopEmployeeCreateRequest request) {
        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                empService.addEmployee(currentUserId, shopId, request), "Thêm nhân viên thành công"
        ));
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<ShopEmployeeResponse>> updateEmployee(
            @PathVariable Integer shopId,
            @PathVariable Integer employeeId,
            @Valid @RequestBody ShopEmployeeUpdateRequest request) {
        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                empService.updateEmployee(currentUserId, shopId, employeeId, request), "Cập nhật thành công"
        ));
    }

    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<Void>> removeEmployee(
            @PathVariable Integer shopId,
            @PathVariable Integer employeeId) {
        Integer currentUserId = securityUtils.getLoggedInUserId();
        empService.removeEmployee(currentUserId, shopId, employeeId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa nhân viên khỏi hệ thống"));
    }
}