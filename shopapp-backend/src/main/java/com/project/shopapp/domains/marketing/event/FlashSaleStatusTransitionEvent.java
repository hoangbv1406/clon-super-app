package com.project.shopapp.domains.marketing.event;

import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class FlashSaleStatusTransitionEvent extends DomainEvent {
    private final Integer flashSaleId;
    private final FlashSaleStatus oldStatus;
    private final FlashSaleStatus newStatus;

    public FlashSaleStatusTransitionEvent(Integer flashSaleId, FlashSaleStatus oldStatus, FlashSaleStatus newStatus) {
        super();
        this.flashSaleId = flashSaleId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }
}