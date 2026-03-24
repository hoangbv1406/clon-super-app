package com.project.shopapp.domains.finance.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentGatewayConfig {
    // TODO: Cấu hình các Bean chứa VNPAY_TMN_CODE, VNPAY_HASH_SECRET, VNPAY_URL.
    // Lấy từ biến môi trường (application.yml / Vault) để ký Hash lúc sinh URL
    // và Verify Hash lúc nhận Webhook IPN.
}