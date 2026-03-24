// --- domains/identity/event/SocialAccountLinkedEvent.java ---
package com.project.shopapp.domains.identity.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class SocialAccountLinkedEvent extends DomainEvent {
    private final Integer userId;
    private final String provider;
    private final String socialEmail;

    public SocialAccountLinkedEvent(Integer userId, String provider, String socialEmail) {
        super();
        this.userId = userId;
        this.provider = provider;
        this.socialEmail = socialEmail;
    }
}