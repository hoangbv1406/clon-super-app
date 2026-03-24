package com.project.shopapp.domains.sales.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplierSettlementJob {
    // TODO: Thiết lập CronJob (ShedLock) chạy vào ngày 1 hàng tháng.
    // Lấy toàn bộ OrderDetail có `supplier_id != null` VÀ `is_settled = false` VÀ `status = DELIVERED`.
    // Gom nhóm (Group By) theo supplier_id, tính tổng `cost_price`.
    // Tạo file Báo cáo Đối soát (Settlement Report Excel) và gọi API Banking để chuyển tiền.
    // Sau khi chuyển xong, update `is_settled = true`.
}