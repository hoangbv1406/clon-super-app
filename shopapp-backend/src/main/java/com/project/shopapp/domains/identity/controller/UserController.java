package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.dto.request.UserRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.UserResponse;
import com.project.shopapp.domains.identity.service.UserService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identity/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject<UserResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(ResponseObject.created(
                userService.registerUser(request), "Đăng ký tài khoản thành công"
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<Void>> softDeleteUser(@PathVariable Integer id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok(ResponseObject.success(null, "Xóa tài khoản thành công"));
    }
}