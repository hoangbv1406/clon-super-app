package com.project.shopapp.domains.finance.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class WalletLockedEvent extends DomainEvent {
    private final Integer walletId;
    private final Integer userId;
    private final String reason;

    public WalletLockedEvent(Integer walletId, Integer userId, String reason) {
        super();
        this.walletId = walletId;
        this.userId = userId;
        this.reason = reason;
    }
}