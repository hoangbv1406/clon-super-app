package com.project.shopapp.domains.marketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@ComponentScan(basePackages = "com.project.shopapp.domains.marketing")
public class MarketingAsyncConfig {

    // ThreadPool riêng biệt tránh việc Banner Click quá nhiều làm treo hệ thống chốt Đơn hàng
    @Bean(name = "marketingTaskExecutor")
    public Executor marketingTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000); // Lưu trữ tối đa 1000 lượt click chờ ghi vào DB
        executor.setThreadNamePrefix("MarketingAsync-");
        executor.initialize();
        return executor;
    }
}