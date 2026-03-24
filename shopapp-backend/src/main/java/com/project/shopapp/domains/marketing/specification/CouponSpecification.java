package com.project.shopapp.domains.marketing.specification;

import com.project.shopapp.domains.marketing.entity.Coupon;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CouponSpecification {
    // Tìm các mã đang Active, chưa hết hạn, và có thể là của Sàn hoặc của 1 Shop cụ thể
    public static Specification<Coupon> getAvailableCoupons(Integer shopId, LocalDateTime now) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("isActive"), true),
                    cb.lessThanOrEqualTo(root.get("startDate"), now),
                    cb.greaterThanOrEqualTo(root.get("endDate"), now)
            );

            // Lấy mã của Shop hiện tại VÀ mã của Sàn (shopId IS NULL)
            if (shopId != null) {
                var shopPredicate = cb.or(
                        cb.isNull(root.get("shopId")),
                        cb.equal(root.get("shopId"), shopId)
                );
                predicate = cb.and(predicate, shopPredicate);
            }
            return predicate;
        };
    }
}