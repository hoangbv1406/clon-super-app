package com.project.shopapp.domains.identity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserResponse user; // Tái sử dụng UserResponse của cậu
}