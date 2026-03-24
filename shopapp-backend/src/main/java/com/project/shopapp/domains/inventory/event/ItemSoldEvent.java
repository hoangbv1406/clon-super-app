// --- event/ItemSoldEvent.java ---
package com.project.shopapp.domains.inventory.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ItemSoldEvent extends DomainEvent {
    private final Integer productItemId;
    private final Integer productId;
    private final Long orderId;

    public ItemSoldEvent(Integer productItemId, Integer productId, Long orderId) {
        super();
        this.productItemId = productItemId;
        this.productId = productId;
        this.orderId = orderId;
    }
}