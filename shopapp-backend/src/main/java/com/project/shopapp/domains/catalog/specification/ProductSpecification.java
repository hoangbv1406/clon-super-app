package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProductSpecification {
    public static Specification<Product> searchAndFilter(String keyword, Integer categoryId, Integer brandId, String vRam) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("isActive"), true)
            );

            if (StringUtils.hasText(keyword)) {
                // Tốt nhất là dùng FULLTEXT Search Native Query, nhưng ở mức JPA Criteria:
                predicate = cb.and(predicate, cb.like(root.get("name"), "%" + keyword + "%"));
            }
            if (categoryId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("categoryId"), categoryId));
            }
            if (brandId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("brandId"), brandId));
            }
            if (StringUtils.hasText(vRam)) {
                // Lọc trên Virtual Column - Bắt index MySQL siêu nhanh
                predicate = cb.and(predicate, cb.equal(root.get("vRam"), vRam));
            }
            return predicate;
        };
    }
}