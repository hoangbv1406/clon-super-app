package com.project.shopapp.domains.social.event;

import com.project.shopapp.domains.social.enums.FollowType;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class NewFollowerEvent extends DomainEvent {
    private final Integer followerId;
    private final FollowType followType;
    private final Integer targetId; // Có thể là userId hoặc shopId

    public NewFollowerEvent(Integer followerId, FollowType followType, Integer targetId) {
        super();
        this.followerId = followerId;
        this.followType = followType;
        this.targetId = targetId;
    }
}