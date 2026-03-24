package com.project.shopapp.domains.sales.repository;

import com.project.shopapp.domains.sales.entity.OrderShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderShopRepository extends JpaRepository<OrderShop, Integer>, JpaSpecificationExecutor<OrderShop> {

    // Lấy các kiện hàng con thuộc 1 đơn hàng lớn
    List<OrderShop> findByParentOrderIdAndIsDeleted(Long parentOrderId, Long isDeleted);

    // Để Vendor tìm đơn của mình
    Optional<OrderShop> findByIdAndShopIdAndIsDeleted(Integer id, Integer shopId, Long isDeleted);
}