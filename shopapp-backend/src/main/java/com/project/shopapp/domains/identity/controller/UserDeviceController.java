package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.identity.dto.request.DeviceRegistrationRequest;
import com.project.shopapp.domains.identity.service.UserDeviceService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications/devices")
@RequiredArgsConstructor
public class UserDeviceController {

    private final UserDeviceService deviceService;
    private final SecurityUtils securityUtils;

    // Mobile App/Frontend gọi API này mỗi lần App mở lên để duy trì luồng Push Token
    @PostMapping("/sync")
    public ResponseEntity<ResponseObject<Void>> syncDeviceToken(@Valid @RequestBody DeviceRegistrationRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        deviceService.registerOrUpdateDevice(userId, request);
        return ResponseEntity.ok(ResponseObject.success(null, "Đồng bộ thiết bị nhận thông báo thành công"));
    }

    // Khi người dùng bấm nút Đăng xuất
    @DeleteMapping("/{deviceUid}")
    public ResponseEntity<ResponseObject<Void>> unregisterDevice(@PathVariable String deviceUid) {
        Integer userId = securityUtils.getLoggedInUserId();
        deviceService.unregisterDevice(userId, deviceUid);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã gỡ thiết bị nhận thông báo"));
    }
}