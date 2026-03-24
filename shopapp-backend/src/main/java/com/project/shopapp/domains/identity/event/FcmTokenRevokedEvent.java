package com.project.shopapp.domains.identity.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class FcmTokenRevokedEvent extends DomainEvent {
    private final String fcmToken;
    private final String reason; // VD: "UNREGISTERED", "INVALID"

    public FcmTokenRevokedEvent(String fcmToken, String reason) {
        super();
        this.fcmToken = fcmToken;
        this.reason = reason;
    }
}