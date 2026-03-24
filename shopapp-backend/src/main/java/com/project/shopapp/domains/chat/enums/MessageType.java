package com.project.shopapp.domains.chat.enums;

public enum MessageType {
    TEXT,       // Văn bản thường
    IMAGE,      // Hình ảnh
    VIDEO,      // Video ngắn
    PRODUCT,    // Card Sản phẩm (Bấm vào nhảy sang trang Chi tiết SP)
    ORDER,      // Card Đơn hàng (Dùng khi hỏi Shop về tình trạng 1 đơn cụ thể)
    SYSTEM      // Bot tự động gửi (VD: "Đơn hàng đã được giao")
}