// --- event/OptionValueChangedEvent.java ---
package com.project.shopapp.domains.catalog.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class OptionValueChangedEvent extends DomainEvent {
    private final Integer optionValueId;
    private final String newValue;

    public OptionValueChangedEvent(Integer optionValueId, String newValue) {
        super();
        this.optionValueId = optionValueId;
        this.newValue = newValue;
    }
}