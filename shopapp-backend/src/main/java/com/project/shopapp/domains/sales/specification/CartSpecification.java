package com.project.shopapp.domains.sales.specification;

import com.project.shopapp.domains.sales.entity.Cart;
import com.project.shopapp.domains.sales.enums.CartStatus;
import org.springframework.data.jpa.domain.Specification;

public class CartSpecification {
    public static Specification<Cart> filterCarts(CartStatus status, Boolean isGuest) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (Boolean.TRUE.equals(isGuest)) {
                predicate = cb.and(predicate, cb.isNull(root.get("userId")));
            } else if (Boolean.FALSE.equals(isGuest)) {
                predicate = cb.and(predicate, cb.isNotNull(root.get("userId")));
            }
            return predicate;
        };
    }
}