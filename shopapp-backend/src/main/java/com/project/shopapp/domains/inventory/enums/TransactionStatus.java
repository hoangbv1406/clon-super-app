package com.project.shopapp.domains.inventory.enums;
public enum TransactionStatus {
    PENDING,    // Đang lưu nháp, chờ duyệt
    COMPLETED,  // Đã duyệt, tồn kho đã thực sự thay đổi
    CANCELLED   // Đã hủy bỏ
}