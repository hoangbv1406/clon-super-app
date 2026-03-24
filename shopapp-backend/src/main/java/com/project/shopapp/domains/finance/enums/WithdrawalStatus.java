package com.project.shopapp.domains.finance.enums;

public enum WithdrawalStatus {
    PENDING,  // Chờ kế toán duyệt
    APPROVED, // Đã chuyển khoản thành công
    REJECTED  // Bị từ chối (Sai thông tin thẻ, nghi ngờ gian lận)
}