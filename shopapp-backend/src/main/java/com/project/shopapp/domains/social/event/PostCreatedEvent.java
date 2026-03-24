package com.project.shopapp.domains.social.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class PostCreatedEvent extends DomainEvent {
    private final Long postId;
    private final Integer authorId;

    public PostCreatedEvent(Long postId, Integer authorId) {
        super();
        this.postId = postId;
        this.authorId = authorId;
    }
}