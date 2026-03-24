package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.dto.request.DeviceRegistrationRequest;
import com.project.shopapp.domains.identity.entity.UserDevice;
import com.project.shopapp.domains.identity.mapper.UserDeviceMapper;
import com.project.shopapp.domains.identity.repository.UserDeviceRepository;
import com.project.shopapp.domains.identity.service.UserDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeviceServiceImpl implements UserDeviceService {

    private final UserDeviceRepository deviceRepository;
    private final UserDeviceMapper deviceMapper;

    @Override
    @Transactional
    public void registerOrUpdateDevice(Integer userId, DeviceRegistrationRequest request) {
        Optional<UserDevice> existingDeviceOpt = deviceRepository.findByUserIdAndDeviceUid(userId, request.getDeviceUid());

        if (existingDeviceOpt.isPresent()) {
            // UPSERT: Thiết bị đã tồn tại, chỉ cập nhật Token mới và thời gian truy cập
            UserDevice device = existingDeviceOpt.get();
            device.setFcmToken(request.getFcmToken());
            device.setDeviceName(request.getDeviceName());
            device.setLastActiveAt(LocalDateTime.now());
            device.setIsActive(true); // Nhỡ trước đó nó bị khóa thì mở lại
            deviceRepository.save(device);
            log.info("Updated FCM Token for device: {}", request.getDeviceUid());
        } else {
            // INSERT: Thiết bị hoàn toàn mới
            UserDevice newDevice = deviceMapper.toEntityFromRequest(request);
            newDevice.setUserId(userId);
            deviceRepository.save(newDevice);
            log.info("Registered new FCM device: {}", request.getDeviceUid());
        }
    }

    @Override
    @Transactional
    public void unregisterDevice(Integer userId, String deviceUid) {
        // Dùng khi user chủ động bấm Đăng xuất khỏi app trên thiết bị này
        deviceRepository.findByUserIdAndDeviceUid(userId, deviceUid).ifPresent(device -> {
            device.setIsActive(false);
            deviceRepository.save(device);
            log.info("Unregistered device: {}", deviceUid);
        });
    }
}