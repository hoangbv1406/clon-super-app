package com.project.shopapp.domains.marketing.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class FlashSaleItemSoldOutEvent extends DomainEvent {
    private final Long flashSaleItemId;
    private final Integer productId;

    public FlashSaleItemSoldOutEvent(Long flashSaleItemId, Integer productId) {
        super();
        this.flashSaleItemId = flashSaleItemId;
        this.productId = productId;
    }
}