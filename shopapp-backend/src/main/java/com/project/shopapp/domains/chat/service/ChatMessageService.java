// --- service/ChatMessageService.java ---
package com.project.shopapp.domains.chat.service;
import com.project.shopapp.domains.chat.dto.request.MessageSendRequest;
import com.project.shopapp.domains.chat.dto.response.MessageResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface ChatMessageService {
    MessageResponse sendMessage(Integer senderId, MessageSendRequest request);
    PageResponse<MessageResponse> getHistory(Integer userId, Integer roomId, int page, int size);
    void recallMessage(Integer senderId, Long messageId);
}