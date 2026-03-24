package com.project.shopapp.domains.chat.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ChatRoomCreatedEvent extends DomainEvent {
    private final Integer roomId;
    private final Integer shopId;
    private final Integer userId; // Khách hàng khởi tạo chat

    public ChatRoomCreatedEvent(Integer roomId, Integer shopId, Integer userId) {
        super();
        this.roomId = roomId;
        this.shopId = shopId;
        this.userId = userId;
    }
}