package com.project.shopapp.domains.inventory.repository;

import com.project.shopapp.domains.inventory.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long>, JpaSpecificationExecutor<InventoryTransaction> {

    Optional<InventoryTransaction> findByTransactionCodeAndShopId(String transactionCode, Integer shopId);

    // Đếm số phiếu giao dịch trong 1 ngày để generate mã tịnh tiến (VD: IN-20260314-001)
    long countByShopIdAndCreatedAtBetween(Integer shopId, java.time.LocalDateTime startOfDay, java.time.LocalDateTime endOfDay);
}