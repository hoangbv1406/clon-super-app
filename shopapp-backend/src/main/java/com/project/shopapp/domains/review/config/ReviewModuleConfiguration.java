package com.project.shopapp.domains.review.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.project.shopapp.domains.review")
public class ReviewModuleConfiguration {
    // Nơi khai báo các Bean đặc thù của Review (Ví dụ: AI Content Moderation Bean để tự động ẩn review chửi bới)
}