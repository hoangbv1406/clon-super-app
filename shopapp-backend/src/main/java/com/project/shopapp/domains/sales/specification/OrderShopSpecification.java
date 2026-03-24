package com.project.shopapp.domains.sales.specification;

import com.project.shopapp.domains.sales.entity.OrderShop;
import com.project.shopapp.domains.sales.enums.OrderShopStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderShopSpecification {
    public static Specification<OrderShop> filterForVendor(Integer shopId, OrderShopStatus status, String orderShopCode) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("shopId"), shopId)
            );

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (orderShopCode != null && !orderShopCode.isBlank()) {
                predicate = cb.and(predicate, cb.like(root.get("orderShopCode"), "%" + orderShopCode + "%"));
            }
            return predicate;
        };
    }
}