package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.Brand;
import com.project.shopapp.domains.catalog.enums.BrandTier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class BrandSpecification {
    public static Specification<Brand> filter(String keyword, BrandTier tier, Boolean isActive) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (StringUtils.hasText(keyword)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }
            if (tier != null) {
                predicate = cb.and(predicate, cb.equal(root.get("tier"), tier));
            }
            if (isActive != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), isActive));
            }
            return predicate;
        };
    }
}
