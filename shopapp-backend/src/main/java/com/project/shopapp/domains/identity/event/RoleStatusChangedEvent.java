package com.project.shopapp.domains.identity.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class RoleStatusChangedEvent extends DomainEvent {
    private final Integer roleId;
    private final String roleName;
    private final boolean isActive;

    public RoleStatusChangedEvent(Integer roleId, String roleName, boolean isActive) {
        super();
        this.roleId = roleId;
        this.roleName = roleName;
        this.isActive = isActive;
    }
}