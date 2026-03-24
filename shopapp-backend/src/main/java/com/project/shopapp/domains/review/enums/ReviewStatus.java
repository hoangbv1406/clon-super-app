package com.project.shopapp.domains.review.enums;

public enum ReviewStatus {
    PENDING,  // Chờ duyệt (nếu hệ thống bật mode kiểm duyệt từ ngữ nhạy cảm)
    APPROVED, // Hiển thị công khai
    REJECTED, // Từ chối do vi phạm tiêu chuẩn cộng đồng
    HIDDEN    // Bị admin ẩn do report
}