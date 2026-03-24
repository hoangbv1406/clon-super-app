package com.project.shopapp.domains.marketing.repository;

import com.project.shopapp.domains.marketing.entity.CouponUsage;
import com.project.shopapp.domains.marketing.enums.CouponUsageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long>, JpaSpecificationExecutor<CouponUsage> {

    // Kiểm tra xem User này đã xài mã này bao nhiêu lần thành công (APPLIED)
    long countByUserIdAndCouponIdAndStatusAndIsDeleted(Integer userId, Integer couponId, CouponUsageStatus status, Long isDeleted);

    // Tìm lịch sử áp dụng của 1 đơn hàng cụ thể
    List<CouponUsage> findByOrderIdAndStatusAndIsDeleted(Long orderId, CouponUsageStatus status, Long isDeleted);

    // Cập nhật trạng thái hàng loạt (Khi Hủy Đơn)
    @Modifying
    @Query("UPDATE CouponUsage c SET c.status = :status WHERE c.orderId = :orderId")
    void updateStatusByOrderId(Long orderId, CouponUsageStatus status);
}