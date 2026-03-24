package com.project.shopapp.domains.identity.dto.nested;

import lombok.Data;

@Data
public class UserDeviceBasicDto {
    private String deviceType; // VD: IOS
    private String deviceName; // VD: iPhone 14 Pro
    private Boolean isActive;
}