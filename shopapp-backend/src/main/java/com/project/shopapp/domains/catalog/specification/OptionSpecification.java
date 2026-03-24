package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.Option;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class OptionSpecification {
    public static Specification<Option> search(String keyword) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);
            if (StringUtils.hasText(keyword)) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("code")), pattern)
                ));
            }
            return predicate;
        };
    }
}