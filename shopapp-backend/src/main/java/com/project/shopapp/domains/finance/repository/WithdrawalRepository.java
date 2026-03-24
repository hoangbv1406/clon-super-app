package com.project.shopapp.domains.finance.repository;

import com.project.shopapp.domains.finance.entity.WithdrawalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends JpaRepository<WithdrawalRequest, Integer>, JpaSpecificationExecutor<WithdrawalRequest> {
    // Tìm các lệnh của một user cụ thể
    Page<WithdrawalRequest> findByUserId(Integer userId, Pageable pageable);
}