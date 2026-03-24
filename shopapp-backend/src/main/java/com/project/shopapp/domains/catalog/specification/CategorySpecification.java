package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> filter(String keyword, Boolean isActive) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);
            if (keyword != null && !keyword.isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }
            if (isActive != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), isActive));
            }
            return predicate;
        };
    }
}