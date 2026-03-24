// --- event/CartItemUpdatedEvent.java ---
package com.project.shopapp.domains.sales.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class CartItemUpdatedEvent extends DomainEvent {
    private final Integer cartId;
    private final Integer productId;
    private final String action; // ADDED, REMOVED, UPDATED

    public CartItemUpdatedEvent(Integer cartId, Integer productId, String action) {
        super();
        this.cartId = cartId;
        this.productId = productId;
        this.action = action;
    }
}