package com.project.shopapp.domains.identity.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class UserDeviceResponse extends BaseResponse {
    private String deviceUid;
    private String deviceType;
    private String deviceName;
    private LocalDateTime lastActiveAt;
    // BẢO MẬT: Không bao giờ trả fcmToken ra API GET, vì lỡ lộ, kẻ gian có thể bắn push rác vào máy khách hàng.
}