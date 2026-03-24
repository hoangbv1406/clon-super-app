package com.project.shopapp.domains.marketing.specification;

import com.project.shopapp.domains.marketing.entity.Banner;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class BannerSpecification {
    public static Specification<Banner> filterForAdmin(String title, String position) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);
            if (StringUtils.hasText(title)) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(position)) {
                predicate = cb.and(predicate, cb.equal(root.get("position"), position));
            }
            return predicate;
        };
    }
}