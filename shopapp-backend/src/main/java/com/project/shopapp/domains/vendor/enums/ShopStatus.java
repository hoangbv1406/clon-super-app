package com.project.shopapp.domains.vendor.enums;

public enum ShopStatus {
    PENDING, // Chờ Admin duyệt hồ sơ
    ACTIVE,  // Đang hoạt động
    BANNED,  // Bị Admin khóa do vi phạm (bán hàng giả)
    CLOSED   // Chủ shop tự đóng cửa
}