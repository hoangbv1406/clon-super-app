package com.project.shopapp.domains.community.enums;

public enum ReviewStatus {
    PENDING,  // Chờ duyệt (Nếu hệ thống bật chế độ kiểm duyệt trước khi hiện)
    APPROVED, // Đang hiển thị bình thường
    REJECTED, // Vi phạm tiêu chuẩn cộng đồng (Tự động lọc bằng AI / Keyword)
    HIDDEN    // Bị người dùng/Shop report và Admin ẩn đi
}