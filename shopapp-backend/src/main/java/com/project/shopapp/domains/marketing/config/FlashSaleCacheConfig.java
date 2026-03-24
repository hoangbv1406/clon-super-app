package com.project.shopapp.domains.marketing.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FlashSaleCacheConfig {
    // TODO: Setup Redis Cache TTL ngắn (ví dụ 1-5 phút) cho các response getItemsByFlashSale
    // để tránh DB sập trong các kỳ Sale lớn như 11/11.
}