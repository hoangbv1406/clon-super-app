package com.project.shopapp.domains.social.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialFeedCacheConfig {
    // TODO: Tích hợp Redis.
    // Viết một Job cứ 5 phút chạy 1 lần. Lấy ra 500 bài viết có điểm Trending cao nhất
    // (Điểm = Like*1 + Comment*3 + Share*5).
    // Nhét 500 bài này vào List của Redis.
    // Khi API getTrendingFeed() được gọi, đọc thẳng từ Redis ra trả về JSON cực tốc độ.
}