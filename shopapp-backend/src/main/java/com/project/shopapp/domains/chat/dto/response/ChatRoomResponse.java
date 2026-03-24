// --- response/ChatRoomResponse.java ---
package com.project.shopapp.domains.chat.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ChatRoomResponse extends BaseResponse {
    private Integer id;
    private String name; // Tên phòng (hoặc tên Shop / Tên Khách hàng tùy theo ai đang nhìn)
    private String avatarUrl; // Avatar hiển thị ngoài list chat
    private Integer shopId;
    private String type;
    private String lastMessage;
    private LocalDateTime updatedAt; // Thời gian tin nhắn cuối
    private Integer unreadCount; // Computed field: Số tin chưa đọc của current user
}