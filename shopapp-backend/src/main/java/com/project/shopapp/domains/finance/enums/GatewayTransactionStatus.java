// --- enums/GatewayTransactionStatus.java ---
package com.project.shopapp.domains.finance.enums;
public enum GatewayTransactionStatus {
    PENDING,    // Vừa tạo link, chờ khách quét mã
    SUCCESS,    // Thanh toán thành công (Webhook báo về)
    FAILED,     // Khách hủy giao dịch hoặc không đủ tiền
    REFUNDED    // Đã được hoàn tiền (dùng cho giao dịch gốc)
}