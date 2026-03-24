// --- event/PaymentSuccessEvent.java ---
package com.project.shopapp.domains.finance.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class PaymentSuccessEvent extends DomainEvent {
    private final Long orderId;
    private final String transactionCode;

    public PaymentSuccessEvent(Long orderId, String transactionCode) {
        super();
        this.orderId = orderId;
        this.transactionCode = transactionCode;
    }
}
