package com.project.shopapp.domains.location.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class ProvinceRegionChangedEvent extends DomainEvent {
    private final String provinceCode;
    private final String oldRegion;
    private final String newRegion;

    public ProvinceRegionChangedEvent(String provinceCode, String oldRegion, String newRegion) {
        super();
        this.provinceCode = provinceCode;
        this.oldRegion = oldRegion;
        this.newRegion = newRegion;
    }
}