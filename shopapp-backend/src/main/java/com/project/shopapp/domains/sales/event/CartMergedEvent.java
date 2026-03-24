// --- event/CartMergedEvent.java ---
package com.project.shopapp.domains.sales.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class CartMergedEvent extends DomainEvent {
    private final Integer userId;
    private final String oldSessionId;
    private final Integer mergedCartId;

    public CartMergedEvent(Integer userId, String oldSessionId, Integer mergedCartId) {
        super();
        this.userId = userId;
        this.oldSessionId = oldSessionId;
        this.mergedCartId = mergedCartId;
    }
}