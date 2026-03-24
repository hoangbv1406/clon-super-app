package com.project.shopapp.domains.social.event;

import com.project.shopapp.domains.social.enums.InteractionType;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class UserInteractedEvent extends DomainEvent {
    private final Integer userId;
    private final Long postId;
    private final Integer productId;
    private final InteractionType action;

    public UserInteractedEvent(Integer userId, Long postId, Integer productId, InteractionType action) {
        super();
        this.userId = userId;
        this.postId = postId;
        this.productId = productId;
        this.action = action;
    }
}