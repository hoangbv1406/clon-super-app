package com.project.shopapp.domains.affiliate.enums;

public enum AffiliateTransStatus {
    PENDING,    // Chờ đối soát (Đơn chưa giao hoặc đang trong thời gian đổi trả)
    APPROVED,   // Đã chốt hợp lệ, chờ đến kỳ thanh toán
    PAID,       // Đã cộng tiền vào Ví (Wallet) của KOC
    CANCELLED,  // Đơn hàng bị hủy trước khi giao
    REVERSED    // Đã giao nhưng khách Trả hàng/Hoàn tiền
}