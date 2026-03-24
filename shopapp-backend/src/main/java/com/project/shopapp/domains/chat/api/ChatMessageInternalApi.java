// --- api/ChatMessageInternalApi.java ---
package com.project.shopapp.domains.chat.api;

public interface ChatMessageInternalApi {
    void sendSystemMessage(Integer roomId, String content);
}