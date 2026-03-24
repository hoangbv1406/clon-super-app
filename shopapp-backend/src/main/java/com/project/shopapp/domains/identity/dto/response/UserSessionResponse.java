package com.project.shopapp.domains.identity.dto.response;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserSessionResponse {
    private Long id;
    private String deviceId;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
    private Boolean isCurrentSession; // Frontend dùng để disable nút "Đăng xuất" của chính session hiện tại
}