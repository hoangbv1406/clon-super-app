package com.project.shopapp.domains.vendor.specification;

import com.project.shopapp.domains.vendor.entity.ShopEmployee;
import com.project.shopapp.domains.vendor.enums.EmployeeStatus;
import com.project.shopapp.domains.vendor.enums.ShopEmployeeRole;
import org.springframework.data.jpa.domain.Specification;

public class ShopEmployeeSpecification {
    public static Specification<ShopEmployee> filter(Integer shopId, ShopEmployeeRole role, EmployeeStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("shopId"), shopId)
            );

            if (role != null) {
                predicate = cb.and(predicate, cb.equal(root.get("role"), role));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            return predicate;
        };
    }
}