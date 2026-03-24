package com.project.shopapp.domains.finance.specification;

import com.project.shopapp.domains.finance.entity.WalletTransaction;
import com.project.shopapp.domains.finance.enums.WalletTransType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class WalletTransSpecification {
    public static Specification<WalletTransaction> filterLedger(Integer walletId, WalletTransType type, LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("walletId"), walletId);

            if (type != null) {
                predicate = cb.and(predicate, cb.equal(root.get("type"), type));
            }
            if (fromDate != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }
            if (toDate != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }
            return predicate;
        };
    }
}