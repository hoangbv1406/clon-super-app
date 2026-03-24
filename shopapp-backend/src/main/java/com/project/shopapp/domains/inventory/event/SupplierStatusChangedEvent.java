// --- event/SupplierStatusChangedEvent.java ---
package com.project.shopapp.domains.inventory.event;

import com.project.shopapp.domains.inventory.enums.SupplierStatus;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class SupplierStatusChangedEvent extends DomainEvent {
    private final Integer supplierId;
    private final Integer shopId;
    private final SupplierStatus newStatus;

    public SupplierStatusChangedEvent(Integer supplierId, Integer shopId, SupplierStatus newStatus) {
        super();
        this.supplierId = supplierId;
        this.shopId = shopId;
        this.newStatus = newStatus;
    }
}