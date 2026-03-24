package com.project.shopapp.domains.catalog.enums;

public enum VariantStatus {
    AVAILABLE,      // Đang bán và còn hàng
    OUT_OF_STOCK,   // Đang bán nhưng tạm hết hàng (quantity - reserved = 0)
    DISCONTINUED    // Ngừng kinh doanh (isActive = false)
}