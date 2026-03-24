// --- response/NotificationResponse.java ---
package com.project.shopapp.domains.notification.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class NotificationResponse extends BaseResponse {
    private Long id;
    private String title;
    private String body;
    private String type;
    private String referenceId;
    private String imageUrl;
    private String deepLink;
    private Boolean isRead;
    private LocalDateTime createdAt;
}