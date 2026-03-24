package com.project.shopapp.domains.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class CatalogAsyncConfig {

    // Thread Pool chuyên biệt xử lý các Event chạy ngầm của riêng Module Catalog
    @Bean(name = "catalogEventTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500); // Có thể nạp 500 event đổi giá cùng lúc
        executor.setThreadNamePrefix("CatalogEvent-");
        executor.initialize();
        return executor;
    }
}