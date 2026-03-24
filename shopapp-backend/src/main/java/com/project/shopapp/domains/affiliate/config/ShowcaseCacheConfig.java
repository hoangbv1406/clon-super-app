package com.project.shopapp.domains.affiliate.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShowcaseCacheConfig {
    // TODO: Tích hợp @Cacheable(value = "koc_showcase", key = "#kocUserId")
    // vào phương thức getPublicShowcase của UserShowcaseServiceImpl.
    // Dữ liệu sẽ được lưu trên RAM (Redis) để load tức thời trong 2ms.
    // Khi KOC thay đổi tủ đồ (Add/Remove/Reorder), gọi @CacheEvict để xóa cacheũ.
}