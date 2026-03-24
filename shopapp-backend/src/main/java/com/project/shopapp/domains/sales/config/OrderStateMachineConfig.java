package com.project.shopapp.domains.sales.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderStateMachineConfig {
    // TODO: Thiết lập Spring StateMachine
    // Cấu hình luồng sự kiện trạng thái Đơn hàng để đảm bảo tính Nhất quán (Consistency).
    // Ví dụ: Chỉ cho phép state transition từ PENDING -> CONFIRMED, cấm PENDING -> DELIVERED.
}