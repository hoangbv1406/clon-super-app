// --- event/PosSessionClosedEvent.java ---
package com.project.shopapp.domains.pos.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class PosSessionClosedEvent extends DomainEvent {
    private final Integer sessionId;
    private final Integer shopId;
    private final Integer userId;
    private final BigDecimal differenceCash; // Truyền đi số tiền bị lệch

    public PosSessionClosedEvent(Integer sessionId, Integer shopId, Integer userId, BigDecimal differenceCash) {
        super();
        this.sessionId = sessionId;
        this.shopId = shopId;
        this.userId = userId;
        this.differenceCash = differenceCash;
    }
}