package com.project.shopapp.domains.after_sales.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WarrantyConfig {
    // TODO: Viết CronJob (ShedLock) chạy mỗi ngày 1 lần.
    // Tìm các phiếu có status = APPROVED và updated_at < (Now - 14 days)
    // VÀ return_tracking_code IS NULL.
    // Tự động update thành REJECTED với Admin Note: "Quá hạn thời gian gửi hàng hoàn trả".
}