package com.project.shopapp.domains.social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AnalyticsAsyncConfig {

    @Bean(name = "analyticsTaskExecutor")
    public Executor analyticsTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // Luôn giữ 10 luồng chực chờ ghi Log
        executor.setMaxPoolSize(50);   // Tối đa 50 luồng
        executor.setQueueCapacity(10000); // Hàng đợi khổng lồ chứa 10.000 tương tác chờ được ghi vào DB
        executor.setThreadNamePrefix("AnalyticsLog-");
        executor.initialize();
        return executor;
    }
}