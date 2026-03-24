// --- service/ChatRoomService.java ---
package com.project.shopapp.domains.chat.service;
import com.project.shopapp.domains.chat.dto.request.ChatRoomCreateRequest;
import com.project.shopapp.domains.chat.dto.response.ChatRoomResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface ChatRoomService {
    ChatRoomResponse initiateChatWithShop(Integer userId, ChatRoomCreateRequest request);
    PageResponse<ChatRoomResponse> getUserInbox(Integer userId, int page, int size);
    PageResponse<ChatRoomResponse> getVendorInbox(Integer shopId, int page, int size);
}