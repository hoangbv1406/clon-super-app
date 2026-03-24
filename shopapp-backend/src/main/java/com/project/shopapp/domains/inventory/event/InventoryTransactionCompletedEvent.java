package com.project.shopapp.domains.inventory.event;

import com.project.shopapp.domains.inventory.enums.TransactionType;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class InventoryTransactionCompletedEvent extends DomainEvent {
    private final Long transactionId;
    private final Integer shopId;
    private final TransactionType type;
    private final BigDecimal totalValue;

    public InventoryTransactionCompletedEvent(Long transactionId, Integer shopId, TransactionType type, BigDecimal totalValue) {
        super();
        this.transactionId = transactionId;
        this.shopId = shopId;
        this.type = type;
        this.totalValue = totalValue;
    }
}