package com.project.shopapp.domains.after_sales.specification;

import com.project.shopapp.domains.after_sales.entity.WarrantyRequest;
import com.project.shopapp.domains.after_sales.enums.WarrantyStatus;
import org.springframework.data.jpa.domain.Specification;

public class WarrantySpecification {
    public static Specification<WarrantyRequest> filterRequests(Integer shopId, Integer userId, WarrantyStatus status, String code) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (shopId != null) predicate = cb.and(predicate, cb.equal(root.get("shopId"), shopId));
            if (userId != null) predicate = cb.and(predicate, cb.equal(root.get("userId"), userId));
            if (status != null) predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            if (code != null && !code.isBlank()) {
                predicate = cb.and(predicate, cb.like(root.get("requestCode"), "%" + code + "%"));
            }
            return predicate;
        };
    }
}