// --- api/ChatParticipantInternalApi.java ---
package com.project.shopapp.domains.chat.api;

public interface ChatParticipantInternalApi {
    void addInitialParticipants(Integer roomId, Integer customerUserId, Integer shopOwnerId);
    boolean isUserInRoom(Integer roomId, Integer userId);
    Long getLastReadMessageId(Integer roomId, Integer userId);
    void wakeUpParticipants(Integer roomId);
}