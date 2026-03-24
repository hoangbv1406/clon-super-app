package com.project.shopapp.domains.review.specification;

import com.project.shopapp.domains.review.entity.ShopReview;
import com.project.shopapp.domains.review.enums.ReviewStatus;
import org.springframework.data.jpa.domain.Specification;

public class ShopReviewSpecification {
    public static Specification<ShopReview> filter(Integer shopId, Byte rating, Boolean hasImages) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("shopId"), shopId),
                    cb.equal(root.get("status"), ReviewStatus.APPROVED),
                    cb.isNull(root.get("parentId")) // Chỉ lấy review gốc, không lấy reply
            );

            if (rating != null) {
                predicate = cb.and(predicate, cb.equal(root.get("rating"), rating));
            }
            if (Boolean.TRUE.equals(hasImages)) {
                // Hibernate 6 JSON check: images is not null and JSON array length > 0
                predicate = cb.and(predicate, cb.isNotNull(root.get("images")));
            }
            return predicate;
        };
    }
}