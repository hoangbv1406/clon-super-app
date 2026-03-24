package com.project.shopapp.domains.marketing.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class CouponBudgetExhaustedEvent extends DomainEvent {
    private final Integer couponId;
    private final String couponCode;

    public CouponBudgetExhaustedEvent(Integer couponId, String couponCode) {
        super();
        this.couponId = couponId;
        this.couponCode = couponCode;
    }
}