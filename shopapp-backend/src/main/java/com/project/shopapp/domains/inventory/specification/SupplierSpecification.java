package com.project.shopapp.domains.inventory.specification;

import com.project.shopapp.domains.inventory.entity.Supplier;
import com.project.shopapp.domains.inventory.enums.SupplierStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SupplierSpecification {
    public static Specification<Supplier> search(Integer shopId, String keyword, SupplierStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("shopId"), shopId)
            );

            if (StringUtils.hasText(keyword)) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(root.get("contactPhone"), pattern),
                        cb.like(root.get("taxCode"), pattern)
                ));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            return predicate;
        };
    }
}