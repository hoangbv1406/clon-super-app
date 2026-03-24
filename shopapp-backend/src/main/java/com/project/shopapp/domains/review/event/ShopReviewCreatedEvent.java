// --- event/ShopReviewCreatedEvent.java ---
package com.project.shopapp.domains.review.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ShopReviewCreatedEvent extends DomainEvent {
    private final Integer shopId;
    private final Byte rating;

    public ShopReviewCreatedEvent(Integer shopId, Byte rating) {
        super();
        this.shopId = shopId;
        this.rating = rating;
    }
}