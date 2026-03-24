package com.project.shopapp.domains.identity.api;

import java.util.List;

public interface UserDeviceInternalApi {
    /**
     * Dành cho các module khác (Order, Chat) gọi để lấy danh sách FCM Token
     * nhằm đẩy Payload Notification qua SDK của Firebase.
     */
    List<String> getActiveTokensForUser(Integer userId);

    /**
     * Callback gọi ngược lại khi SDK Firebase ném Exception Token lỗi
     */
    void handleDeadToken(String token, String errorReason);
}