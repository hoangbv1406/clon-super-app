package com.project.shopapp.domains.identity.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserLockedEvent extends DomainEvent {
    private final String email;
    private final String ipAddress;

    public UserLockedEvent(String email, String ipAddress) {
        super();
        this.email = email;
        this.ipAddress = ipAddress;
    }
}