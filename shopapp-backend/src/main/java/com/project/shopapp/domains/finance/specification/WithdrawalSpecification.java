package com.project.shopapp.domains.finance.specification;

import com.project.shopapp.domains.finance.entity.WithdrawalRequest;
import com.project.shopapp.domains.finance.enums.WithdrawalStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class WithdrawalSpecification {
    public static Specification<WithdrawalRequest> filterForAdmin(WithdrawalStatus status, Integer userId, LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (status != null) predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            if (userId != null) predicate = cb.and(predicate, cb.equal(root.get("userId"), userId));
            if (from != null) predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdAt"), from));
            if (to != null) predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdAt"), to));

            return predicate;
        };
    }
}