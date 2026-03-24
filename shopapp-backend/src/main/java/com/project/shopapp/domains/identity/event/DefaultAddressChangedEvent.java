// --- domains/identity/event/DefaultAddressChangedEvent.java ---
package com.project.shopapp.domains.identity.event;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class DefaultAddressChangedEvent extends DomainEvent {
    private final Integer userId;
    private final String newProvinceCode;
    private final String newDistrictCode;

    public DefaultAddressChangedEvent(Integer userId, String provinceCode, String districtCode) {
        super();
        this.userId = userId;
        this.newProvinceCode = provinceCode;
        this.newDistrictCode = districtCode;
    }
}