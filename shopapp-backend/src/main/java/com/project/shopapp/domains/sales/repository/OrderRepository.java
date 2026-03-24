package com.project.shopapp.domains.sales.repository;

import com.project.shopapp.domains.sales.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    // Đây chính là hàm mà OrderInternalApiImpl đang gào thét đòi hỏi nè:
    Optional<Order> findByOrderCodeAndIsDeleted(String orderCode, Long isDeleted);

    // Nếu cậu cần các hàm Custom Query khác, cứ bế từ file cũ sang đây nhé!
    // Phục vụ việc sinh mã đơn hàng tuần tự theo ngày
    long countByOrderDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    // Lấy danh sách đơn hàng của User (có phân trang)
    Page<Order> findByUserIdAndIsDeleted(Integer userId, Long isDeleted, Pageable pageable);
}