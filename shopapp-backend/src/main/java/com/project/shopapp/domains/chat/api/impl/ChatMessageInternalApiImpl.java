// --- api/impl/ChatMessageInternalApiImpl.java ---
package com.project.shopapp.domains.chat.api.impl;

import com.project.shopapp.domains.chat.api.ChatMessageInternalApi;
import com.project.shopapp.domains.chat.api.ChatRoomInternalApi;
import com.project.shopapp.domains.chat.entity.ChatMessage;
import com.project.shopapp.domains.chat.enums.MessageType;
import com.project.shopapp.domains.chat.event.MessageBroadcastEvent;
import com.project.shopapp.domains.chat.mapper.ChatMessageMapper;
import com.project.shopapp.domains.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageInternalApiImpl implements ChatMessageInternalApi {

    private final ChatMessageRepository messageRepo;
    private final ChatRoomInternalApi roomApi;
    private final ChatMessageMapper messageMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void sendSystemMessage(Integer roomId, String content) {
        ChatMessage msg = ChatMessage.builder()
                .roomId(roomId)
                .senderId(0) // 0 = Hệ thống
                .content(content)
                .type(MessageType.SYSTEM)
                .build();

        ChatMessage saved = messageRepo.save(msg);

        // Đẩy phòng chat lên top
        roomApi.updateLastMessage(roomId, "[Thông báo hệ thống]");

        // Bắn ra WebSocket
        eventPublisher.publishEvent(new MessageBroadcastEvent(messageMapper.toDto(saved), roomId, 0));
    }
}