// --- event/CategoryTreeChangedEvent.java ---
package com.project.shopapp.domains.catalog.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class CategoryTreeChangedEvent extends DomainEvent {
    private final String action; // CREATE, UPDATE, DELETE
    private final Integer categoryId;

    public CategoryTreeChangedEvent(String action, Integer categoryId) {
        super();
        this.action = action;
        this.categoryId = categoryId;
    }
}