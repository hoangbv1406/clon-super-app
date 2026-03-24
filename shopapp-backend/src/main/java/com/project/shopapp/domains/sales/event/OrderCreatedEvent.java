// --- event/OrderCreatedEvent.java ---
package com.project.shopapp.domains.sales.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrderCreatedEvent extends DomainEvent {
    private final Long orderId;
    private final String orderCode;
    private final Integer userId;
    private final Integer cartId; // Truyền cartId để listener dọn rác

    public OrderCreatedEvent(Long orderId, String orderCode, Integer userId, Integer cartId) {
        super();
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.userId = userId;
        this.cartId = cartId;
    }
}