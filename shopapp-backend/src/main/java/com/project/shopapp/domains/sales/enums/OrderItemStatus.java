package com.project.shopapp.domains.sales.enums;

public enum OrderItemStatus {
    NORMAL,             // Hàng bình thường
    RETURN_REQUESTED,   // Khách đang yêu cầu trả hàng món này
    RETURNED,           // Đã trả hàng và hoàn tiền món này
    WARRANTY_PROCESSING // Đang trong quá trình bảo hành (nếu cần thiết)
}