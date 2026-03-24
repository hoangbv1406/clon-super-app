package com.project.shopapp.domains.identity.dto.nested;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSessionNestedDto {
    private Long id;
    private String deviceId;
    private String userAgent;
    private String ipAddress;
    private LocalDateTime expiresAt;
    private Boolean isRevoked; // Admin nhìn vào đây để biết thiết bị nào đã bị kích ra
}