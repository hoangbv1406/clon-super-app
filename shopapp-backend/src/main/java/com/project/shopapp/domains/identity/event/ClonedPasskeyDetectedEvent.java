package com.project.shopapp.domains.identity.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ClonedPasskeyDetectedEvent extends DomainEvent {
    private final Integer userId;
    private final String credentialId;

    public ClonedPasskeyDetectedEvent(Integer userId, String credentialId) {
        super();
        this.userId = userId;
        this.credentialId = credentialId;
    }
}