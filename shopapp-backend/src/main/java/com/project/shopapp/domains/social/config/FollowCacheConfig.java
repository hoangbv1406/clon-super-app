package com.project.shopapp.domains.social.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FollowCacheConfig {
    // TODO: Áp dụng Redis Caching.
    // Khi Shop có người Follow mới (bắt event NewFollowerEvent), ta sẽ INCR (Cộng 1) vào Redis Key `shop:{id}:followers`.
    // Khi khách vào trang Shop, ta lấy số liệu hiển thị MỘT CÁCH TỨC THỜI từ Redis thay vì DB.
}