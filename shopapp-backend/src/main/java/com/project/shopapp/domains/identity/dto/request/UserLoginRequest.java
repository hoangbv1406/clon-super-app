package com.project.shopapp.domains.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank(message = "Tài khoản (Email/SĐT) không được để trống")
    private String username; // Có thể là Email hoặc Phone

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;
}