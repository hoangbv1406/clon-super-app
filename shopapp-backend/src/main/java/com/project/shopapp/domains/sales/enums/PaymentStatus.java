// --- enums/PaymentStatus.java ---
package com.project.shopapp.domains.sales.enums;
public enum PaymentStatus {
    UNPAID,         // Chưa thanh toán
    PAID,           // Đã thanh toán thành công
    REFUNDED,       // Đã hoàn tiền
    FAILED          // Thanh toán thất bại
}