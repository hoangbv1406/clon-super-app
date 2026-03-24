package com.project.shopapp.domains.inventory.specification;

import com.project.shopapp.domains.inventory.entity.InventoryTransaction;
import com.project.shopapp.domains.inventory.enums.TransactionStatus;
import com.project.shopapp.domains.inventory.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecification {
    public static Specification<InventoryTransaction> filter(Integer shopId, TransactionType type, TransactionStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("shopId"), shopId);

            if (type != null) predicate = cb.and(predicate, cb.equal(root.get("type"), type));
            if (status != null) predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            if (startDate != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
            if (endDate != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), endDate));

            return predicate;
        };
    }
}