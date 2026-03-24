// --- event/OptionStatusChangedEvent.java ---
package com.project.shopapp.domains.catalog.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class OptionStatusChangedEvent extends DomainEvent {
    private final Integer optionId;
    private final boolean isActive;

    public OptionStatusChangedEvent(Integer optionId, boolean isActive) {
        super();
        this.optionId = optionId;
        this.isActive = isActive;
    }
}