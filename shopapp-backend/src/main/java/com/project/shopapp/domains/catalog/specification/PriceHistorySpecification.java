package com.project.shopapp.domains.catalog.specification;

import com.project.shopapp.domains.catalog.entity.PriceHistory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PriceHistorySpecification {
    public static Specification<PriceHistory> filterByDateRange(Integer productId, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("productId"), productId);

            if (startDate != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
            }
            if (endDate != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), endDate));
            }
            return predicate;
        };
    }
}