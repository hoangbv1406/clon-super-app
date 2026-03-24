package com.project.shopapp.domains.catalog.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ProductImageDeletedEvent extends DomainEvent {
    private final Integer imageId;
    private final String imageUrl; // Chứa key để S3 biết đường xóa

    public ProductImageDeletedEvent(Integer imageId, String imageUrl) {
        super();
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }
}