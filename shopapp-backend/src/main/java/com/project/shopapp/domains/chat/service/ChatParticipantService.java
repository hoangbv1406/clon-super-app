// --- service/ChatParticipantService.java ---
package com.project.shopapp.domains.chat.service;
import com.project.shopapp.domains.chat.dto.request.ParticipantSettingsRequest;

public interface ChatParticipantService {
    void updateSettings(Integer userId, Integer roomId, ParticipantSettingsRequest request);
    void hideChatRoom(Integer userId, Integer roomId);
    void markAsRead(Integer userId, Integer roomId, Long messageId);
}