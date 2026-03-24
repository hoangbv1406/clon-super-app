package com.project.shopapp.domains.finance.repository;

import com.project.shopapp.domains.finance.entity.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>, JpaSpecificationExecutor<WalletTransaction> {

    // Tìm lịch sử biến động số dư của 1 ví
    Page<WalletTransaction> findByWalletId(Integer walletId, Pageable pageable);
}