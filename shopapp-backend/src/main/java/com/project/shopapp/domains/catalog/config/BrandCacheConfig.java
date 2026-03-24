package com.project.shopapp.domains.catalog.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BrandCacheConfig {
    // TODO: Thiết lập RedisCacheManager Custom Builder dành riêng cho name = "brands_list"
    // Cài đặt TTL (Time-To-Live) = 12 Hours.
}