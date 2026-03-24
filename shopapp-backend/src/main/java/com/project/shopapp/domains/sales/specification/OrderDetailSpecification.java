package com.project.shopapp.domains.sales.specification;

import com.project.shopapp.domains.sales.entity.OrderDetail;
import org.springframework.data.jpa.domain.Specification;

public class OrderDetailSpecification {
    // Dành cho Vendor lọc các mặt hàng đang bị Yêu cầu trả hàng
    public static Specification<OrderDetail> filterReturnRequests(Integer shopId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.join("orderShop").get("shopId"), shopId),
                cb.equal(root.get("itemStatus"), "RETURN_REQUESTED")
        );
    }
}