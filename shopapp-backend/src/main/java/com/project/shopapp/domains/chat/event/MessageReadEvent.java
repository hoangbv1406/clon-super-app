package com.project.shopapp.domains.chat.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class MessageReadEvent extends DomainEvent {
    private final Integer roomId;
    private final Integer userId;
    private final Long messageId;

    public MessageReadEvent(Integer roomId, Integer userId, Long messageId) {
        super();
        this.roomId = roomId;
        this.userId = userId;
        this.messageId = messageId;
    }
}