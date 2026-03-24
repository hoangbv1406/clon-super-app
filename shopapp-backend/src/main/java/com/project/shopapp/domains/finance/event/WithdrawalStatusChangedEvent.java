package com.project.shopapp.domains.finance.event;

import com.project.shopapp.domains.finance.enums.WithdrawalStatus;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class WithdrawalStatusChangedEvent extends DomainEvent {
    private final Integer withdrawalId;
    private final Integer userId;
    private final WithdrawalStatus newStatus;

    public WithdrawalStatusChangedEvent(Integer withdrawalId, Integer userId, WithdrawalStatus newStatus) {
        super();
        this.withdrawalId = withdrawalId;
        this.userId = userId;
        this.newStatus = newStatus;
    }
}