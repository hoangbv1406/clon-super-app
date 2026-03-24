package com.project.shopapp.domains.location.enums;

public enum DeliveryStatus {
    AVAILABLE,     // Giao bình thường
    SUSPENDED,     // Tạm ngưng (bão lũ, dịch bệnh)
    OUT_OF_ZONE    // Nằm ngoài vùng phủ sóng của đơn vị vận chuyển
}