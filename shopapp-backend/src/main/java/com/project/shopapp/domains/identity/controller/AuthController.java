package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.dto.request.UserLoginRequest;
import com.project.shopapp.domains.identity.dto.response.LoginResponse;
import com.project.shopapp.domains.identity.service.AuthService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix:/api/v1}/identity/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject<LoginResponse>> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpServletRequest httpRequest) {

        // Trích xuất IP và Device-ID (từ header) để phục vụ tracking bảo mật
        String ipAddress = httpRequest.getRemoteAddr();
        String deviceId = httpRequest.getHeader("User-Agent"); // Hoặc một header custom như X-Device-ID

        LoginResponse loginResponse = authService.login(request, ipAddress, deviceId);

        return ResponseEntity.ok(ResponseObject.success(
                loginResponse, "Đăng nhập thành công"
        ));
    }
}