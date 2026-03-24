package com.project.shopapp.domains.sales.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class PartialItemReturnRequestedEvent extends DomainEvent {
    private final Integer orderDetailId;
    private final Long orderId;

    public PartialItemReturnRequestedEvent(Integer orderDetailId, Long orderId) {
        super();
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
    }
}