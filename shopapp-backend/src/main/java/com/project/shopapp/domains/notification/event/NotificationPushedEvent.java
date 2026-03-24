package com.project.shopapp.domains.notification.event;

import com.project.shopapp.domains.notification.entity.Notification;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class NotificationPushedEvent extends DomainEvent {
    private final Notification notificationPayload; // Chứa data để Firebase bắn đi

    public NotificationPushedEvent(Notification notificationPayload) {
        super();
        this.notificationPayload = notificationPayload;
    }
}