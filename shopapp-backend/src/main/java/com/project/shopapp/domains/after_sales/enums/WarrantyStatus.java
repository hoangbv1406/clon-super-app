// --- enums/WarrantyStatus.java ---
package com.project.shopapp.domains.after_sales.enums;
public enum WarrantyStatus {
    PENDING,    // Mới tạo, chờ Shop xác nhận
    APPROVED,   // Shop đồng ý cho gửi hàng về
    RECEIVED,   // Shop đã nhận được hàng khách gửi
    PROCESSING, // Đang kiểm tra/Sửa chữa
    COMPLETED,  // Đã sửa xong/Đã hoàn tiền xong
    REJECTED    // Từ chối (Lỗi do người dùng làm rơi vỡ, hết hạn...)
}