package com.project.shopapp.domains.identity.dto.nested;
import lombok.Data;

@Data
public class SocialAccountBasicDto {
    private String provider;
    private String email;
    // Dùng để show trong trang "Tài khoản của tôi -> Các tài khoản liên kết"
}