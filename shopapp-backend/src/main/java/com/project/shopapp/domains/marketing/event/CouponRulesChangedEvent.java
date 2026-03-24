package com.project.shopapp.domains.marketing.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class CouponRulesChangedEvent extends DomainEvent {
    private final Integer couponId;
    private final String action; // ADDED, REMOVED

    public CouponRulesChangedEvent(Integer couponId, String action) {
        super();
        this.couponId = couponId;
        this.action = action;
    }
}