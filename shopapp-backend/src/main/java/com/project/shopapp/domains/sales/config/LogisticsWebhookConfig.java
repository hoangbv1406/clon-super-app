package com.project.shopapp.domains.sales.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LogisticsWebhookConfig {
    // TODO: Cấu hình URL endpoint và Secret Key để nhận Webhook từ GHTK / GHN / NinjaVan.
    // Khi shipper báo "Giao thành công", Webhook gọi về -> Tự động kích hoạt markAsDelivered()
}