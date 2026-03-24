package com.project.shopapp.domains.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Kích hoạt WebSockets
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Khách hàng sẽ "Subscribe" (Lắng nghe) vào các URL có tiền tố này để nhận tin nhắn mới
        config.enableSimpleBroker("/topic", "/queue");

        // Client gửi tin nhắn lên Server thông qua tiền tố này (VD: /app/chat.sendMessage)
        config.setApplicationDestinationPrefixes("/app");

        // Dành cho tin nhắn gửi đích danh 1 User cụ thể (User-to-User)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint để Frontend kết nối vào: ws://domain.com/ws-chat
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") // Cho phép Cross-Origin lúc dev
                .withSockJS(); // Tương thích ngược với các trình duyệt cũ không hỗ trợ Native WebSocket
    }
}