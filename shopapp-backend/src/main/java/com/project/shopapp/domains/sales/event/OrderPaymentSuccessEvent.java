// --- event/OrderPaymentSuccessEvent.java ---
package com.project.shopapp.domains.sales.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrderPaymentSuccessEvent extends DomainEvent {
    private final Long orderId;
    private final String orderCode;

    public OrderPaymentSuccessEvent(Long orderId, String orderCode) {
        super();
        this.orderId = orderId;
        this.orderCode = orderCode;
    }
}