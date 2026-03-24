package com.project.shopapp.domains.location.specification;

import com.project.shopapp.domains.location.entity.Ward;
import com.project.shopapp.domains.location.enums.DeliveryStatus;
import org.springframework.data.jpa.domain.Specification;

public class WardSpecification {
    public static Specification<Ward> isDeliveryStatus(DeliveryStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("deliveryStatus"), status);
    }
}