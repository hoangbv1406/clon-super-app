package com.project.shopapp.domains.identity.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class SuspiciousActivityEvent extends DomainEvent {
    private final Integer userId;
    private final String ipAddress;
    private final String reason;

    public SuspiciousActivityEvent(Integer userId, String ipAddress, String reason) {
        super();
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.reason = reason;
    }
}