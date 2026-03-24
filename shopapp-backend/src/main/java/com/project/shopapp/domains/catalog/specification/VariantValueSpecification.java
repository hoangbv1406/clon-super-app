package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.VariantValue;
import org.springframework.data.jpa.domain.Specification;

public class VariantValueSpecification {
    public static Specification<VariantValue> filterMatrix(Integer productId, Integer optionId) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("productId"), productId);
            if (optionId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("optionId"), optionId));
            }
            return predicate;
        };
    }
}