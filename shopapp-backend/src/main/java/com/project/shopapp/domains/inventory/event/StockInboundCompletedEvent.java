package com.project.shopapp.domains.inventory.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class StockInboundCompletedEvent extends DomainEvent {
    private final Integer variantId;
    private final Integer quantityAdded;
    private final BigDecimal unitCost;

    public StockInboundCompletedEvent(Integer variantId, Integer quantityAdded, BigDecimal unitCost) {
        super();
        this.variantId = variantId;
        this.quantityAdded = quantityAdded;
        this.unitCost = unitCost;
    }
}