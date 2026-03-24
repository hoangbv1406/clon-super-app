// --- event/ShopEmployeeResignedEvent.java ---
package com.project.shopapp.domains.vendor.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ShopEmployeeResignedEvent extends DomainEvent {
    private final Integer shopId;
    private final Integer userId;

    public ShopEmployeeResignedEvent(Integer shopId, Integer userId) {
        super();
        this.shopId = shopId;
        this.userId = userId;
    }
}