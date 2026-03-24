package com.project.shopapp.domains.location.specification;

import com.project.shopapp.domains.location.entity.Province;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProvinceSpecification {
    public static Specification<Province> filter(String keyword, String region, Boolean isActive) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
            if (StringUtils.hasText(keyword)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(region)) {
                predicate = cb.and(predicate, cb.equal(root.get("region"), region));
            }
            if (isActive != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isActive"), isActive));
            }
            return predicate;
        };
    }
}