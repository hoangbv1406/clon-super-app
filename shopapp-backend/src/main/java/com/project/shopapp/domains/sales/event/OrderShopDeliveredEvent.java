package com.project.shopapp.domains.sales.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class OrderShopDeliveredEvent extends DomainEvent {
    private final Integer orderShopId;
    private final Integer shopId;
    private final Long parentOrderId;
    private final BigDecimal shopIncome;

    public OrderShopDeliveredEvent(Integer orderShopId, Integer shopId, Long parentOrderId, BigDecimal shopIncome) {
        super();
        this.orderShopId = orderShopId;
        this.shopId = shopId;
        this.parentOrderId = parentOrderId;
        this.shopIncome = shopIncome;
    }
}