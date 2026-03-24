package com.project.shopapp.domains.community.specification;

import com.project.shopapp.domains.community.entity.ProductReview;
import com.project.shopapp.domains.community.enums.ReviewStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProductReviewSpecification {

    // PUBLIC: Khách hàng lọc Review theo số Sao hoặc xem Review có hình ảnh
    public static Specification<ProductReview> filterForPublic(Integer productId, Integer rating, Boolean hasImages) {
        return (root, query, cb) -> {
            var predicate = cb.and(
                    cb.equal(root.get("isDeleted"), 0L),
                    cb.equal(root.get("productId"), productId),
                    cb.isNull(root.get("parentId")), // Chỉ lấy Review gốc, không lấy Reply
                    cb.equal(root.get("status"), ReviewStatus.APPROVED)
            );

            if (rating != null) {
                predicate = cb.and(predicate, cb.equal(root.get("rating"), rating));
            }
            if (Boolean.TRUE.equals(hasImages)) {
                // Kiểm tra cột JSON images không rỗng
                predicate = cb.and(predicate, cb.isNotNull(root.get("images")));
                // Ở MySQL thực tế có thể cần cb.isTrue(cb.function("JSON_LENGTH", Integer.class, root.get("images")) > 0)
            }
            return predicate;
        };
    }
}