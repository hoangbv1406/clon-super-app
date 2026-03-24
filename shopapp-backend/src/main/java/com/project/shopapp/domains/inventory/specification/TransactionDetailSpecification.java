package com.project.shopapp.domains.inventory.specification;

import com.project.shopapp.domains.inventory.entity.InventoryTransactionDetail;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionDetailSpecification {
    public static Specification<InventoryTransactionDetail> stockCardFilter(Integer variantId, LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            var predicate = cb.equal(root.get("variantId"), variantId);

            // Chỉ lấy các dòng của những Phiếu đã COMPLETED (Tồn kho thực sự đã đổi)
            predicate = cb.and(predicate, cb.equal(root.join("transaction").get("status"), "COMPLETED"));

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