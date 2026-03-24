package com.project.shopapp.domains.identity.service;
import com.project.shopapp.domains.identity.dto.request.DeviceRegistrationRequest;

public interface UserDeviceService {
    void registerOrUpdateDevice(Integer userId, DeviceRegistrationRequest request);
    void unregisterDevice(Integer userId, String deviceUid);
}