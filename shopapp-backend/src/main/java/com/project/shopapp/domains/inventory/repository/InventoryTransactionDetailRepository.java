package com.project.shopapp.domains.inventory.repository;

import com.project.shopapp.domains.inventory.entity.InventoryTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryTransactionDetailRepository extends JpaRepository<InventoryTransactionDetail, Long>, JpaSpecificationExecutor<InventoryTransactionDetail> {

    // Lấy toàn bộ dòng chi tiết của 1 phiếu kho
    List<InventoryTransactionDetail> findByTransactionId(Long transactionId);

}