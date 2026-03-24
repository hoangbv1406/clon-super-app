package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.ProductImage;
import com.project.shopapp.domains.catalog.enums.ProductImageType;
import org.springframework.data.jpa.domain.Specification;

public class ProductImageSpecification {
    public static Specification<ProductImage> filterByType(ProductImageType type) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);
            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("imageType"), type));
            }
            return predicate;
        };
    }
}