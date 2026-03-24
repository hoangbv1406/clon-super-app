// --- request/ChatRoomCreateRequest.java ---
package com.project.shopapp.domains.chat.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRoomCreateRequest {
    @NotNull(message = "Cần chỉ định Shop để bắt đầu trò chuyện")
    private Integer shopId;

    // Nếu tạo Group thì truyền name, nếu Private thì không cần
    private String name;
}