package com.project.shopapp.domains.identity.specification;

import com.project.shopapp.domains.identity.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {
    public static Specification<Role> filter(String keyword, Boolean isActive) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
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