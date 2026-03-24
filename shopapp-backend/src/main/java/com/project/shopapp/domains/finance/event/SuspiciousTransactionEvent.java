package com.project.shopapp.domains.finance.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class SuspiciousTransactionEvent extends DomainEvent {
    private final Long transactionId;
    private final Integer walletId;
    private final BigDecimal amount;

    public SuspiciousTransactionEvent(Long transactionId, Integer walletId, BigDecimal amount) {
        super();
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.amount = amount;
    }
}