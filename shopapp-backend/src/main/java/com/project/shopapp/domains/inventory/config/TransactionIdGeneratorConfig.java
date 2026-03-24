package com.project.shopapp.domains.inventory.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionIdGeneratorConfig {
    // TODO: Tích hợp Redis AtomicLong hoặc Twitter Snowflake Algorithm
    // để thay thế hàm sinh mã Phiếu kho (Transaction Code) tránh thắt cổ chai DB.
}