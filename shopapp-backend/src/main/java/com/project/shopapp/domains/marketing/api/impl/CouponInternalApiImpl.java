// --- api/impl/CouponInternalApiImpl.java ---
package com.project.shopapp.domains.marketing.api.impl;
import com.project.shopapp.domains.marketing.api.CouponInternalApi;
import com.project.shopapp.domains.marketing.entity.Coupon;
import com.project.shopapp.domains.marketing.enums.CouponDiscountType;
import com.project.shopapp.domains.marketing.repository.CouponRepository;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CouponInternalApiImpl implements CouponInternalApi {

    private final CouponRepository couponRepo;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDiscount(String code, Integer shopId, BigDecimal orderTotal) {
        Coupon coupon = couponRepo.findByCodeAndIsDeleted(code, 0L)
                .orElseThrow(() -> new InvalidParamException("Mã giảm giá không tồn tại"));

        if (!coupon.getIsActive() || coupon.isExpired()) {
            throw new InvalidParamException("Mã giảm giá đã hết hạn hoặc không khả dụng");
        }
        if (coupon.getShopId() != null && !coupon.getShopId().equals(shopId)) {
            throw new InvalidParamException("Mã giảm giá này không áp dụng cho gian hàng hiện tại");
        }
        if (orderTotal.compareTo(coupon.getMinOrderAmount()) < 0) {
            throw new InvalidParamException("Đơn hàng chưa đạt giá trị tối thiểu (" + coupon.getMinOrderAmount() + ") để áp mã");
        }
        if (coupon.isOutOfLimit() || coupon.isOutOfBudget()) {
            throw new InvalidParamException("Mã giảm giá đã hết lượt sử dụng hoặc vượt quá ngân sách");
        }

        BigDecimal discountAmt = BigDecimal.ZERO;
        if (coupon.getDiscountType() == CouponDiscountType.FIXED_AMOUNT) {
            discountAmt = coupon.getDiscountValue();
        } else if (coupon.getDiscountType() == CouponDiscountType.PERCENTAGE) {
            discountAmt = orderTotal.multiply(coupon.getDiscountValue()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            // Ràng buộc giới hạn giảm tối đa
            if (coupon.getMaxDiscountAmount() != null && discountAmt.compareTo(coupon.getMaxDiscountAmount()) > 0) {
                discountAmt = coupon.getMaxDiscountAmount();
            }
        }

        // Không được giảm giá vượt quá giá trị đơn hàng
        return discountAmt.compareTo(orderTotal) > 0 ? orderTotal : discountAmt;
    }

    @Override
    @Transactional
    public boolean consumeCoupon(Integer couponId, BigDecimal discountAmount) {
        int rows = couponRepo.consumeCoupon(couponId, discountAmount);
        return rows > 0;
    }

    @Override
    @Transactional
    public void refundCoupon(Integer couponId, BigDecimal discountAmount) {
        couponRepo.refundCoupon(couponId, discountAmount);
    }
}