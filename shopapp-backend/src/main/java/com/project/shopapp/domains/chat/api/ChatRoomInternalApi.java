// --- api/ChatRoomInternalApi.java ---
package com.project.shopapp.domains.chat.api;

public interface ChatRoomInternalApi {
    // Xin cấp (hoặc tạo mới) ID phòng chat giữa Khách và Shop
    Integer getOrProvisionPrivateRoom(Integer userId, Integer shopId);

    // Cập nhật Trích lược tin nhắn và thời gian để đẩy phòng này lên đầu danh sách
    void updateLastMessage(Integer roomId, String messageSnippet);
}