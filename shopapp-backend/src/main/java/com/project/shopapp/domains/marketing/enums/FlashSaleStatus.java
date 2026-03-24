package com.project.shopapp.domains.marketing.enums;

public enum FlashSaleStatus {
    PENDING,   // Sắp diễn ra (Chưa tới start_time)
    ACTIVE,    // Đang diễn ra
    ENDED,     // Đã kết thúc (Qua end_time)
    CANCELLED  // Bị hủy giữa chừng do Admin
}