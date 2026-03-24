package com.project.shopapp.domains.finance.repository;

import com.project.shopapp.domains.finance.entity.Transaction;
import com.project.shopapp.domains.finance.enums.GatewayTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {

    // Tìm Giao dịch thanh toán gốc của 1 Đơn hàng
    Optional<Transaction> findByOrderIdAndType(Long orderId, GatewayTransactionType type);

    // Tìm Giao dịch theo Mã tham chiếu nội bộ (VD: vnp_TxnRef) để xử lý IPN
    Optional<Transaction> findById(Integer id);
}