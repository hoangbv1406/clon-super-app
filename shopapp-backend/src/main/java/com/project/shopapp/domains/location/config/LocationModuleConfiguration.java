package com.project.shopapp.domains.location.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LocationModuleConfiguration {
    // TODO: Custom CacheManager Builder với TTL = 24h riêng cho vùng dữ liệu "provinces"
    // Cấu hình các Bean đặc thù của riêng location module tại đây để tránh phình to ở shared.
}