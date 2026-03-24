package com.project.shopapp.domains.marketing.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CouponConfig {
    // TODO: Thiết lập các cấu hình RateLimiting (Sử dụng Bucket4j hoặc Redis Rate Limiter)
    // để chặn các công cụ Bot tự động quét dò tìm (Brute-force) mã giảm giá.
}