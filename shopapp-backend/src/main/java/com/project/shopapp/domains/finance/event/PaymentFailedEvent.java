// --- event/PaymentFailedEvent.java ---
package com.project.shopapp.domains.finance.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class PaymentFailedEvent extends DomainEvent {
    private final Long orderId;
    public PaymentFailedEvent(Long orderId) { super(); this.orderId = orderId; }
}