package com.project.shopapp.domains.chat.event;

import com.project.shopapp.domains.chat.dto.response.MessageResponse;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class MessageBroadcastEvent extends DomainEvent {
    private final MessageResponse message; // Payload tin nhắn gửi ra WebSockets
    private final Integer roomId;
    private final Integer senderId;

    public MessageBroadcastEvent(MessageResponse message, Integer roomId, Integer senderId) {
        super();
        this.message = message;
        this.roomId = roomId;
        this.senderId = senderId;
    }
}