package com.project.shopapp.domains.sales.specification;

import com.project.shopapp.domains.sales.entity.Order;
import com.project.shopapp.domains.sales.enums.OrderStatus;
import com.project.shopapp.domains.sales.enums.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecification {
    public static Specification<Order> searchOrders(String keyword, OrderStatus status, PaymentStatus paymentStatus, LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                var keyPredicate = cb.or(
                        cb.like(cb.lower(root.get("orderCode")), pattern),
                        cb.like(root.get("phoneNumber"), pattern)
                );
                predicate = cb.and(predicate, keyPredicate);
            }
            if (status != null) predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            if (paymentStatus != null) predicate = cb.and(predicate, cb.equal(root.get("paymentStatus"), paymentStatus));
            if (fromDate != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("orderDate"), fromDate));
            if (toDate != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("orderDate"), toDate));

            return predicate;
        };
    }
}