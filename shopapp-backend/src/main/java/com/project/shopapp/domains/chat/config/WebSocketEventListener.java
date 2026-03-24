package com.project.shopapp.domains.chat.config;

import com.project.shopapp.domains.chat.event.MessageBroadcastEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    // private final NotificationInternalApi notiApi; // Dùng để đẩy FCM nếu user offline

    /**
     * Bất đồng bộ: Lắng nghe Event từ DB và Bắn ra Socket
     */
    @Async
    @EventListener
    public void handleMessageBroadcast(MessageBroadcastEvent event) {
        Integer roomId = event.getRoomId();

        // 1. PUSH TỚI WEBSOCKET TOPIC (Những user đang mở màn hình chat sẽ nhận được ngay lập tức)
        String topic = "/topic/rooms/" + roomId;
        messagingTemplate.convertAndSend(topic, event.getMessage());
        log.info("Đã bắn tin nhắn {} ra WebSocket topic: {}", event.getMessage().getId(), topic);

        // 2. TODO: Gọi bảng UserSessions hoặc Participant để xem đối phương có đang ONLINE không.
        // Nếu Offline -> Gọi NotificationInternalApi để đẩy Firebase Push Notification (Ting ting!).
    }
}