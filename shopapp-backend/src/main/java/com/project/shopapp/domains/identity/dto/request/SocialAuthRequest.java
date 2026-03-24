package com.project.shopapp.domains.identity.dto.request;
import com.project.shopapp.domains.identity.validation.ValidSocialProvider;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SocialAuthRequest {
    @ValidSocialProvider
    private String provider; // GOOGLE, FACEBOOK

    @NotBlank(message = "Token từ Provider không được để trống")
    private String accessToken; // Token mà Frontend lấy được từ SDK của Google/Facebook
}