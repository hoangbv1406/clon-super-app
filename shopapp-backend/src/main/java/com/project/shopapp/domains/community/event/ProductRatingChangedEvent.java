package com.project.shopapp.domains.community.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ProductRatingChangedEvent extends DomainEvent {
    private final Integer productId;
    private final float newAverageRating;
    private final int newReviewCount;

    public ProductRatingChangedEvent(Integer productId, float newAverageRating, int newReviewCount) {
        super();
        this.productId = productId;
        this.newAverageRating = newAverageRating;
        this.newReviewCount = newReviewCount;
    }
}