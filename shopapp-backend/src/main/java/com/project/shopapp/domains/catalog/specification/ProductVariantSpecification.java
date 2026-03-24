package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.ProductVariant;
import org.springframework.data.jpa.domain.Specification;

public class ProductVariantSpecification {
    public static Specification<ProductVariant> belongsToProduct(Integer productId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("isDeleted"), 0L),
                cb.equal(root.get("productId"), productId)
        );
    }
}