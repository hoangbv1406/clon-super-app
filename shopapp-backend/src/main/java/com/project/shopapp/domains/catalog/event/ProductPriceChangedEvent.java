package com.project.shopapp.domains.catalog.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductPriceChangedEvent extends DomainEvent {
    private final Integer productId;
    private final Integer variantId; // Có thể null nếu đổi giá gốc của Product không có Variant
    private final BigDecimal oldPrice;
    private final BigDecimal newPrice;
    private final String reason;
    private final Integer updatedBy;

    public ProductPriceChangedEvent(Integer productId, Integer variantId,
                                    BigDecimal oldPrice, BigDecimal newPrice,
                                    String reason, Integer updatedBy) {
        super();
        this.productId = productId;
        this.variantId = variantId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.reason = reason;
        this.updatedBy = updatedBy;
    }
}