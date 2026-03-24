// --- event/VariantOutOfStockEvent.java ---
package com.project.shopapp.domains.catalog.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class VariantOutOfStockEvent extends DomainEvent {
    private final Integer variantId;
    private final Integer productId;
    private final String variantName; // Tên tự sinh (VD: Đỏ - 256GB)

    public VariantOutOfStockEvent(Integer variantId, Integer productId, String variantName) {
        super();
        this.variantId = variantId;
        this.productId = productId;
        this.variantName = variantName;
    }
}