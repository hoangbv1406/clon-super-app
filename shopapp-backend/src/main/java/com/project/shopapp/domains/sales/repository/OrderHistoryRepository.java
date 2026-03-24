package com.project.shopapp.domains.sales.repository;

import com.project.shopapp.domains.sales.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    // Phục vụ vẽ Timeline Đơn tổng (Sắp xếp mới nhất lên đầu)
    List<OrderHistory> findByOrderIdAndOrderShopIdIsNullOrderByCreatedAtDesc(Long orderId);

    // Phục vụ vẽ Timeline Kiện hàng con của Shop (Sắp xếp mới nhất lên đầu)
    List<OrderHistory> findByOrderShopIdOrderByCreatedAtDesc(Integer orderShopId);
}