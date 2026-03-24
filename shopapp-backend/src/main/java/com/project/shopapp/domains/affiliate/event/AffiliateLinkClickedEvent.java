package com.project.shopapp.domains.affiliate.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class AffiliateLinkClickedEvent extends DomainEvent {
    private final String code;
    private final String ipAddress;
    private final String userAgent;

    public AffiliateLinkClickedEvent(String code, String ipAddress, String userAgent) {
        super();
        this.code = code;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
}