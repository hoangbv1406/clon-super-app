package com.project.shopapp.domains.inventory.enums;

public enum ItemStatus {
    AVAILABLE,  // Sẵn sàng bán
    HOLD,       // Đang bị giữ trong giỏ hàng chờ thanh toán (có locked_until)
    PENDING,    // Đang đóng gói / Giao cho shipper
    SOLD,       // Đã giao thành công, ghi nhận doanh thu
    DEFECTIVE,  // Hàng lỗi, móp méo
    WARRANTY    // Đang nhận bảo hành từ khách
}