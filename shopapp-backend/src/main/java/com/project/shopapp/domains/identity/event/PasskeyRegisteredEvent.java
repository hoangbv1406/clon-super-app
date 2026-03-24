package com.project.shopapp.domains.identity.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class PasskeyRegisteredEvent extends DomainEvent {
    private final Integer userId;
    private final String deviceLabel;

    public PasskeyRegisteredEvent(Integer userId, String deviceLabel) {
        super();
        this.userId = userId;
        this.deviceLabel = deviceLabel;
    }
}