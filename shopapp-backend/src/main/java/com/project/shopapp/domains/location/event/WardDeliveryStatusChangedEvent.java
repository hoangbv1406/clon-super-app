package com.project.shopapp.domains.location.event;

import com.project.shopapp.domains.location.enums.DeliveryStatus;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class WardDeliveryStatusChangedEvent extends DomainEvent {
    private final String wardCode;
    private final DeliveryStatus oldStatus;
    private final DeliveryStatus newStatus;
    private final String reason;

    public WardDeliveryStatusChangedEvent(String wardCode, DeliveryStatus oldStatus, DeliveryStatus newStatus, String reason) {
        super();
        this.wardCode = wardCode;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
    }
}