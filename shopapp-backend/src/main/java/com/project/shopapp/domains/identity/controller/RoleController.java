package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.dto.request.RoleRequest;
import com.project.shopapp.domains.identity.dto.response.RoleResponse;
import com.project.shopapp.domains.identity.service.RoleService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ResponseObject<List<RoleResponse>>> getAllRoles() {
        return ResponseEntity.ok(ResponseObject.success(roleService.getAllRoles()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<RoleResponse>> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(ResponseObject.created(
                roleService.createRole(request), "Tạo mới Role thành công"
        ));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<RoleResponse>> updateStatus(
            @PathVariable Integer id,
            @RequestParam boolean isActive) {
        return ResponseEntity.ok(ResponseObject.success(
                roleService.updateRoleStatus(id, isActive), "Cập nhật trạng thái Role thành công"
        ));
    }
}