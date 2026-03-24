package com.project.shopapp.domains.marketing.enums;

public enum CouponUsageStatus {
    APPLIED,   // Lượt dùng hợp lệ, đang trừ vào ngân sách Coupon
    REVERTED   // Khách hủy đơn, lượt dùng này đã được hoàn trả
}