package com.project.shopapp.domains.marketing.specification;

import com.project.shopapp.domains.marketing.entity.CouponUsage;
import com.project.shopapp.domains.marketing.enums.CouponUsageStatus;
import org.springframework.data.jpa.domain.Specification;

public class CouponUsageSpecification {
    public static Specification<CouponUsage> searchForAdmin(Integer couponId, Integer userId, CouponUsageStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);
            if (couponId != null) predicate = cb.and(predicate, cb.equal(root.get("couponId"), couponId));
            if (userId != null) predicate = cb.and(predicate, cb.equal(root.get("userId"), userId));
            if (status != null) predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            return predicate;
        };
    }
}