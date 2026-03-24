package com.project.shopapp.domains.marketing.repository;

import com.project.shopapp.domains.marketing.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>, JpaSpecificationExecutor<Coupon> {

    boolean existsByCodeAndIsDeleted(String code, Long isDeleted);

    Optional<Coupon> findByCodeAndIsDeleted(String code, Long isDeleted);
    Optional<Coupon> findByIdAndIsDeleted(Integer id, Long isDeleted);

    // [QUAN TRỌNG]: Consume (Trừ quỹ) nguyên tử ở cấp Database
    @Modifying
    @Query("UPDATE Coupon c SET c.usedCount = c.usedCount + 1, " +
            "c.usedBudget = c.usedBudget + :discountAmount, c.version = c.version + 1 " +
            "WHERE c.id = :couponId AND c.isActive = true AND c.isDeleted = 0 " +
            "AND (c.usageLimit IS NULL OR c.usedCount < c.usageLimit) " +
            "AND (c.totalBudget IS NULL OR (c.usedBudget + :discountAmount) <= c.totalBudget)")
    int consumeCoupon(Integer couponId, BigDecimal discountAmount);

    // Hàm Hoàn lại (Hoàn tiền vào quỹ khi khách Hủy đơn hàng)
    @Modifying
    @Query("UPDATE Coupon c SET c.usedCount = c.usedCount - 1, " +
            "c.usedBudget = c.usedBudget - :discountAmount, c.version = c.version + 1 " +
            "WHERE c.id = :couponId AND c.usedCount > 0")
    int refundCoupon(Integer couponId, BigDecimal discountAmount);
}