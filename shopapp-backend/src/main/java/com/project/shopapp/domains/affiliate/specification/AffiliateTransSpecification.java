package com.project.shopapp.domains.affiliate.specification;

import com.project.shopapp.domains.affiliate.entity.AffiliateTransaction;
import com.project.shopapp.domains.affiliate.enums.AffiliateTransStatus;
import org.springframework.data.jpa.domain.Specification;

public class AffiliateTransSpecification {
    public static Specification<AffiliateTransaction> searchForAdmin(Integer userId, AffiliateTransStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
            if (userId != null) {
                predicate = cb.and(predicate, cb.equal(root.join("affiliateLink").get("userId"), userId));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            return predicate;
        };
    }
}