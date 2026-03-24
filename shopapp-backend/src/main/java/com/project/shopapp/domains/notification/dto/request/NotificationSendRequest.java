// --- request/NotificationSendRequest.java ---
package com.project.shopapp.domains.notification.dto.request;
import com.project.shopapp.domains.notification.validation.ValidNotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationSendRequest {
    // DTO này dùng cho Admin/Marketing đẩy Push Notification thủ công bằng CMS
    private Integer userId; // Có thể null nếu muốn gửi Broadcast (Hàng loạt)

    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotBlank(message = "Nội dung không được để trống")
    private String body;

    @ValidNotificationType
    private String type;

    private String imageUrl;
    private String deepLink;
}