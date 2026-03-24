package com.project.shopapp.domains.catalog.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class VariantMatrixSyncedEvent extends DomainEvent {
    private final Integer productId;

    public VariantMatrixSyncedEvent(Integer productId) {
        super();
        this.productId = productId;
    }
}