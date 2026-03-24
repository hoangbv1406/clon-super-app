package com.project.shopapp.domains.marketing.specification;

import com.project.shopapp.domains.marketing.entity.FlashSale;
import com.project.shopapp.domains.marketing.enums.FlashSaleStatus;
import org.springframework.data.jpa.domain.Specification;

public class FlashSaleSpecification {
    public static Specification<FlashSale> search(Integer shopId, FlashSaleStatus status) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("isDeleted"), 0L);

            if (shopId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("shopId"), shopId));
            } else {
                predicate = cb.and(predicate, cb.isNull(root.get("shopId"))); // Lấy của Platform
            }

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            return predicate;
        };
    }
}