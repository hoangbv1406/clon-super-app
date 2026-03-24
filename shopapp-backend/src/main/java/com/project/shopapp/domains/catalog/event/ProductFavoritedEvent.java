package com.project.shopapp.domains.catalog.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ProductFavoritedEvent extends DomainEvent {
    private final Integer userId;
    private final Integer productId;
    private final boolean isAdded; // True = Like, False = Unlike

    public ProductFavoritedEvent(Integer userId, Integer productId, boolean isAdded) {
        super();
        this.userId = userId;
        this.productId = productId;
        this.isAdded = isAdded;
    }
}