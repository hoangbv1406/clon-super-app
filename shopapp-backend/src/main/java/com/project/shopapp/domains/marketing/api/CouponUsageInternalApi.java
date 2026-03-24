// --- api/CouponUsageInternalApi.java ---
package com.project.shopapp.domains.marketing.api;
import java.math.BigDecimal;

public interface CouponUsageInternalApi {
    // 1. Kiểm tra xem User còn lượt dùng Voucher này không
    void checkUserUsageLimit(Integer userId, Integer couponId);

    // 2. Ghi nhận lịch sử dùng (Gọi ngay khi Order được tạo)
    void recordUsage(Integer userId, Integer couponId, Long orderId, BigDecimal discountAmount);

    // 3. Hoàn trả lượt dùng (Gọi khi Hủy Đơn)
    void revertCouponUsageForOrder(Long orderId);
}