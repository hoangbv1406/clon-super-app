package com.project.shopapp.domains.identity.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken; // Token mới được sinh ra
    private String tokenType;
    private Long expiresIn;
}