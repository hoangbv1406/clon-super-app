// --- event/BannerClickedEvent.java ---
package com.project.shopapp.domains.marketing.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class BannerClickedEvent extends DomainEvent {
    private final Integer bannerId;
    // Có thể truyền thêm userId nếu muốn track xem Ai là người click

    public BannerClickedEvent(Integer bannerId) {
        super();
        this.bannerId = bannerId;
    }
}