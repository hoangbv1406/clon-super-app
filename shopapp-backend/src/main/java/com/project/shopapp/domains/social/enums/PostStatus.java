// --- enums/PostStatus.java ---
package com.project.shopapp.domains.social.enums;
public enum PostStatus {
    PENDING,    // Chờ duyệt (Nếu hệ thống gắt gao)
    APPROVED,   // Đang hiển thị trên Feed
    REJECTED,   // Bị AI/Admin từ chối do vi phạm
    HIDDEN      // Bị chủ bài viết ẩn đi hoặc bị report nhiều quá
}