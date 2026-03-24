// --- request/MessageSendRequest.java ---
package com.project.shopapp.domains.chat.dto.request;
import com.project.shopapp.domains.chat.validation.ValidMessagePayload;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Map;

@Data
@ValidMessagePayload // Validate chéo các field dựa vào Type
public class MessageSendRequest {
    @NotNull(message = "ID Phòng không được để trống")
    private Integer roomId;

    private String content;

    @NotNull(message = "Loại tin nhắn không được để trống")
    private String type;

    private Map<String, Object> attachmentUrl; // Chứa Payload của Image/Video/Product
    private Long replyToId;
}