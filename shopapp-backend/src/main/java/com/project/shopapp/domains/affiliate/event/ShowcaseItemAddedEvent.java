package com.project.shopapp.domains.affiliate.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ShowcaseItemAddedEvent extends DomainEvent {
    private final Integer kocUserId;
    private final Integer productId;

    public ShowcaseItemAddedEvent(Integer kocUserId, Integer productId) {
        super();
        this.kocUserId = kocUserId;
        this.productId = productId;
    }
}