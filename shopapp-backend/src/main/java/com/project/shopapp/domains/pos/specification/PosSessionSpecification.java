package com.project.shopapp.domains.pos.specification;

import com.project.shopapp.domains.pos.entity.PosSession;
import com.project.shopapp.domains.pos.enums.PosSessionStatus;
import org.springframework.data.jpa.domain.Specification;

public class PosSessionSpecification {
    public static Specification<PosSession> filterSessions(Integer shopId, PosSessionStatus status, Boolean hasDiscrepancy) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("shopId"), shopId);

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (Boolean.TRUE.equals(hasDiscrepancy)) {
                // differenceCash != 0
                predicate = cb.and(predicate, cb.notEqual(root.get("differenceCash"), 0));
            }
            return predicate;
        };
    }
}