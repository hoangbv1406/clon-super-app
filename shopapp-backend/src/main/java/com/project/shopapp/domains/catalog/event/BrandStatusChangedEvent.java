package com.project.shopapp.domains.catalog.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class BrandStatusChangedEvent extends DomainEvent {
    private final Integer brandId;
    private final boolean isActive;

    public BrandStatusChangedEvent(Integer brandId, boolean isActive) {
        super();
        this.brandId = brandId;
        this.isActive = isActive;
    }
}