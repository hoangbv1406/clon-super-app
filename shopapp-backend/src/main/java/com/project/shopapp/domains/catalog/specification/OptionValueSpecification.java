package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.OptionValue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class OptionValueSpecification {
    public static Specification<OptionValue> filterByOptionAndKeyword(Integer optionId, String keyword) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("optionId"), optionId)
            );
            if (StringUtils.hasText(keyword)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("value")), "%" + keyword.toLowerCase() + "%"));
            }
            return predicate;
        };
    }
}