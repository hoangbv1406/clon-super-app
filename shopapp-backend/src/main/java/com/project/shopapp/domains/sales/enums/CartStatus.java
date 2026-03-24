package com.project.shopapp.domains.sales.enums;

public enum CartStatus {
    ACTIVE, // User đang tự do thêm, sửa, xóa sản phẩm trong giỏ
    LOCKED  // Đang trong tiến trình thanh toán (Checkout), cấm thay đổi Cart Items
}