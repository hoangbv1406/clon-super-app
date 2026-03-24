package com.project.shopapp.domains.location.event;

import com.project.shopapp.domains.location.enums.DistrictType;
import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class DistrictTypeChangedEvent extends DomainEvent {
    private final String districtCode;
    private final DistrictType oldType;
    private final DistrictType newType;

    public DistrictTypeChangedEvent(String districtCode, DistrictType oldType, DistrictType newType) {
        super();
        this.districtCode = districtCode;
        this.oldType = oldType;
        this.newType = newType;
    }
}