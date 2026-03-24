package com.project.shopapp.domains.finance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class WalletRetryConfig {
    // TODO: Áp dụng @Retryable(value = OptimisticLockingFailureException.class, maxAttempts = 3)
    // tại các hàm trong WalletInternalApiImpl để hệ thống tự động retry khi gặp tranh chấp dữ liệu (Race Condition).
}