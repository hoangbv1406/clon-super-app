package com.project.shopapp.domains.identity.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserRegisteredEvent extends DomainEvent {
    private final Integer userId;
    private final String email;
    private final String fullName;

    public UserRegisteredEvent(Integer userId, String email, String fullName) {
        super();
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
    }
}