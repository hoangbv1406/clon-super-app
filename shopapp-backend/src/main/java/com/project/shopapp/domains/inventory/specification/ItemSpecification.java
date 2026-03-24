package com.project.shopapp.domains.inventory.specification;

import com.project.shopapp.domains.inventory.entity.ProductItem;
import com.project.shopapp.domains.inventory.enums.ItemStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ItemSpecification {
    public static Specification<ProductItem> filter(Integer productId, Integer variantId, String imei, ItemStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (productId != null) predicate = cb.and(predicate, cb.equal(root.get("productId"), productId));
            if (variantId != null) predicate = cb.and(predicate, cb.equal(root.get("variantId"), variantId));
            if (status != null) predicate = cb.and(predicate, cb.equal(root.get("status"), status));

            if (StringUtils.hasText(imei)) {
                predicate = cb.and(predicate, cb.like(root.get("imeiCode"), "%" + imei + "%"));
            }
            return predicate;
        };
    }
}