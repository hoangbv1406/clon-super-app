package com.project.shopapp.domains.identity.dto.request;
import com.project.shopapp.domains.identity.validation.ValidDeviceType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceRegistrationRequest {
    @NotBlank(message = "FCM Token không được để trống")
    private String fcmToken;

    @NotBlank(message = "Mã thiết bị (UID) không được để trống")
    private String deviceUid;

    @ValidDeviceType
    private String deviceType;

    private String deviceName; // VD: "iPhone 14 Pro"
}