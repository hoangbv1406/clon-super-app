package com.project.shopapp.domains.sales.specification;

import com.project.shopapp.domains.sales.entity.CartItem;
import org.springframework.data.jpa.domain.Specification;

public class CartItemSpecification {
    public static Specification<CartItem> findAbandonedProducts(Integer productId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("productId"), productId),
                cb.equal(root.join("cart").get("status"), "ABANDONED") // Tham chiếu sang bảng Header
        );
    }
}