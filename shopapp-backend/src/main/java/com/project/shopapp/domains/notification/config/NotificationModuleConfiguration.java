package com.project.shopapp.domains.notification.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Điểm entry-point để cấu hình riêng cho Module Notification.
 * Nếu sau này tách Microservices, file này sẽ biến thành lớp Application có chứa @SpringBootApplication.
 */
@Configuration
@ComponentScan(basePackages = "com.project.shopapp.domains.notification")
public class NotificationModuleConfiguration {

    // TODO: Define các Bean dùng chung toàn cục cho riêng module Notification tại đây
    // Ví dụ: Bean RestTemplate/WebClient riêng biệt có set Timeout để gọi qua Firebase API

}