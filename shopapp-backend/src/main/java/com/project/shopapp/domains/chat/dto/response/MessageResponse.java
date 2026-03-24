// --- response/MessageResponse.java ---
package com.project.shopapp.domains.chat.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class MessageResponse extends BaseResponse {
    private Long id;
    private Integer roomId;
    private Integer senderId;
    private String content;
    private String type;
    private Map<String, Object> attachmentUrl;
    private Long replyToId;
    private Boolean isDeleted; // Nếu true, Frontend hiện "Tin nhắn đã bị thu hồi"
    private LocalDateTime createdAt;
}