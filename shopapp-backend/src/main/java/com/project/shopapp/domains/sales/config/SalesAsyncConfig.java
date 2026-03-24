package com.project.shopapp.domains.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SalesAsyncConfig {

    @Bean(name = "salesEventTaskExecutor")
    public Executor salesEventTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500); // Hàng đợi 500 log thay đổi trạng thái
        executor.setThreadNamePrefix("SalesEvent-");
        executor.initialize();
        return executor;
    }
}