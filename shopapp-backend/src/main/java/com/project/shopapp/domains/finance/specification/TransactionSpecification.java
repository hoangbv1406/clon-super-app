package com.project.shopapp.domains.finance.specification;

import com.project.shopapp.domains.finance.entity.Transaction;
import com.project.shopapp.domains.finance.enums.GatewayTransactionStatus;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
    public static Specification<Transaction> searchForAdmin(String orderCode, GatewayTransactionStatus status, String method) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction(); // Khởi tạo predicate rỗng (Luôn True)

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (method != null && !method.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("paymentMethod"), method));
            }
            if (orderCode != null && !orderCode.isBlank()) {
                predicate = cb.and(predicate, cb.like(root.join("order").get("orderCode"), "%" + orderCode + "%"));
            }

            return predicate;
        };
    }
}