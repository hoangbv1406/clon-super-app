package com.project.shopapp.domains.notification.config;

import com.project.shopapp.domains.notification.event.NotificationPushedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Configuration
@Slf4j
public class FirebasePushConfig {

    // TODO: Khởi tạo FirebaseApp Bean tại đây bằng file serviceAccountKey.json
    // cấp từ Google Firebase Console.

    /**
     * Listener này hoạt động Độc lập và Bất đồng bộ (Async).
     * Khi có 1 Notification được save vào DB, nó sẽ bắt tín hiệu và đẩy ra Mobile/Web.
     */
    @Async
    @EventListener
    public void handlePushNotification(NotificationPushedEvent event) {
        log.info("Chuẩn bị đẩy Push Notification (FCM) cho User ID: {}", event.getNotificationPayload().getUserId());

        // Luồng giả lập:
        // 1. Gọi UserInternalApi để lấy mảng `fcm_token` từ bảng `user_devices` của User này.
        // 2. Nếu User đang offline/đã thoát app, gửi HTTP POST sang FCM Endpoint: https://fcm.googleapis.com/fcm/send
        // 3. Payload truyền sang: { "title": "...", "body": "...", "data": { "deep_link": "..." } }
        // 4. Điện thoại của User "Rung" lên và hiện màn hình thông báo!
    }
}