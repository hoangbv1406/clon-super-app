package com.project.shopapp.domains.finance.enums;

public enum WalletStatus {
    ACTIVE, // Hoạt động bình thường, được phép rút tiền
    LOCKED  // Bị khóa (Do Admin nghi ngờ gian lận hoặc Shop vi phạm chính sách)
}