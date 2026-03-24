package com.project.shopapp.domains.vendor.specification;

import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.domains.vendor.enums.ShopStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ShopSpecification {
    public static Specification<Shop> filterShops(String keyword, ShopStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (StringUtils.hasText(keyword)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            return predicate;
        };
    }
}