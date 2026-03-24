package com.project.shopapp.domains.sales.enums;
public enum OrderStatus {
    PENDING,        // Mới tạo, chờ xử lý / chờ thanh toán
    CONFIRMED,      // Đã xác nhận, chia đơn cho các Shop
    PROCESSING,     // Đang đóng gói
    SHIPPED,        // Đang giao hàng
    DELIVERED,      // Đã giao thành công
    CANCELLED,      // Đã hủy (bởi user hoặc hệ thống)
    RETURNED        // Đã hoàn trả
}