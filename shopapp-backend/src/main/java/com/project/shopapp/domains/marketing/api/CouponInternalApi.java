package com.project.shopapp.domains.marketing.api;
import com.project.shopapp.domains.marketing.dto.nested.CouponBasicDto;
import java.math.BigDecimal;

public interface CouponInternalApi {
    // Tính toán số tiền được giảm nếu áp mã này (Chưa trừ vào quỹ)
    BigDecimal calculateDiscount(String code, Integer shopId, BigDecimal orderTotal);

    // Thực sự trừ vào ngân sách (Khi user ấn Thanh toán)
    boolean consumeCoupon(Integer couponId, BigDecimal discountAmount);

    // Trả lại ngân sách khi khách Hủy đơn
    void refundCoupon(Integer couponId, BigDecimal discountAmount);
}