package com.project.shopapp.domains.sales.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrderCancelledEvent extends DomainEvent {
    private final Long orderId;

    public OrderCancelledEvent(Long orderId) {
        super();
        this.orderId = orderId;
    }
}