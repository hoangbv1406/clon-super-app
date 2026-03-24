package com.project.shopapp.domains.sales.enums;

public enum OrderShopStatus {
    PENDING,          // Chờ Shop xác nhận
    PROCESSING,       // Shop đang chuẩn bị hàng / đóng gói
    SHIPPED,          // Đã giao cho đơn vị vận chuyển (GHTK/GHN)
    DELIVERED,        // Giao hàng thành công tới khách
    DELIVERY_FAILED,  // Giao thất bại (Khách bom hàng)
    CANCELLED,        // Bị hủy (Bởi khách hoặc Shop)
    RETURNED          // Khách trả hàng hoàn tiền
}