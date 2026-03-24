// --- api/impl/CouponUsageInternalApiImpl.java ---
package com.project.shopapp.domains.marketing.api.impl;

import com.project.shopapp.domains.marketing.api.CouponInternalApi; // Đã tạo ở bài Coupon
import com.project.shopapp.domains.marketing.api.CouponUsageInternalApi;
import com.project.shopapp.domains.marketing.entity.Coupon;
import com.project.shopapp.domains.marketing.entity.CouponUsage;
import com.project.shopapp.domains.marketing.enums.CouponUsageStatus;
import com.project.shopapp.domains.marketing.repository.CouponRepository;
import com.project.shopapp.domains.marketing.repository.CouponUsageRepository;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponUsageInternalApiImpl implements CouponUsageInternalApi {

    private final CouponUsageRepository usageRepo;
    private final CouponRepository couponRepo;
    private final CouponInternalApi couponApi;

    @Override
    @Transactional(readOnly = true)
    public void checkUserUsageLimit(Integer userId, Integer couponId) {
        Coupon coupon = couponRepo.findByIdAndIsDeleted(couponId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Voucher không tồn tại"));

        if (coupon.getUsagePerUser() != null) {
            long usedCount = usageRepo.countByUserIdAndCouponIdAndStatusAndIsDeleted(
                    userId, couponId, CouponUsageStatus.APPLIED, 0L);

            if (usedCount >= coupon.getUsagePerUser()) {
                throw new ConflictException("Bạn đã hết lượt sử dụng mã giảm giá này!");
            }
        }
    }

    @Override
    @Transactional
    public void recordUsage(Integer userId, Integer couponId, Long orderId, BigDecimal discountAmount) {
        // 1. Kiểm tra lại lượt của User lần cuối (Tránh Double-click)
        checkUserUsageLimit(userId, couponId);

        // 2. Trừ quỹ (Atomic update ở bảng Coupon)
        boolean consumed = couponApi.consumeCoupon(couponId, discountAmount);
        if (!consumed) {
            throw new ConflictException("Mã giảm giá đã hết lượt hoặc hết ngân sách!");
        }

        // 3. Ghi lịch sử
        CouponUsage usage = CouponUsage.builder()
                .userId(userId)
                .couponId(couponId)
                .orderId(orderId)
                .discountAmount(discountAmount)
                .status(CouponUsageStatus.APPLIED)
                .createdBy(userId) // System/User tự sinh
                .build();

        usageRepo.save(usage);
    }

    @Override
    @Transactional
    public void revertCouponUsageForOrder(Long orderId) {
        List<CouponUsage> usages = usageRepo.findByOrderIdAndStatusAndIsDeleted(orderId, CouponUsageStatus.APPLIED, 0L);
        if (usages.isEmpty()) return;

        for (CouponUsage usage : usages) {
            // Hoàn lại tiền/lượt cho quỹ tổng của Voucher
            couponApi.refundCoupon(usage.getCouponId(), usage.getDiscountAmount());
        }

        // Cập nhật trạng thái lịch sử
        usageRepo.updateStatusByOrderId(orderId, CouponUsageStatus.REVERTED);
    }
}