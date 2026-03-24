// --- domains/vendor/event/ShopStatusChangedEvent.java ---
package com.project.shopapp.domains.vendor.event;
import com.project.shopapp.domains.vendor.enums.ShopStatus;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ShopStatusChangedEvent extends DomainEvent {
    private final Integer shopId;
    private final ShopStatus oldStatus;
    private final ShopStatus newStatus;
    private final String reason;

    public ShopStatusChangedEvent(Integer shopId, ShopStatus oldStatus, ShopStatus newStatus, String reason) {
        super();
        this.shopId = shopId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.reason = reason;
    }
}