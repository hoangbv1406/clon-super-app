package com.project.shopapp.domains.affiliate.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class AffiliateCommissionApprovedEvent extends DomainEvent {
    private final Integer kocUserId;
    private final BigDecimal amount;

    public AffiliateCommissionApprovedEvent(Integer kocUserId, BigDecimal amount) {
        super();
        this.kocUserId = kocUserId;
        this.amount = amount;
    }
}