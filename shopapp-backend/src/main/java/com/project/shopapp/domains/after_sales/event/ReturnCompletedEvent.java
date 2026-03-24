// --- event/ReturnCompletedEvent.java ---
package com.project.shopapp.domains.after_sales.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ReturnCompletedEvent extends DomainEvent {
    private final Long orderId; // Lấy từ OrderDetail
    private final Integer orderShopId;
    private final BigDecimal refundAmount;
    private final String requestCode;

    public ReturnCompletedEvent(Long orderId, Integer orderShopId, BigDecimal refundAmount, String requestCode) {
        super();
        this.orderId = orderId;
        this.orderShopId = orderShopId;
        this.refundAmount = refundAmount;
        this.requestCode = requestCode;
    }
}