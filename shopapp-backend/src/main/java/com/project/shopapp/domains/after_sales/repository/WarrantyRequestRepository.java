package com.project.shopapp.domains.after_sales.repository;

import com.project.shopapp.domains.after_sales.entity.WarrantyRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarrantyRequestRepository extends JpaRepository<WarrantyRequest, Integer>, JpaSpecificationExecutor<WarrantyRequest> {

    // Đếm số lượng phiếu hôm nay để Gen Request Code
    long countByCreatedAtBetween(java.time.LocalDateTime startOfDay, java.time.LocalDateTime endOfDay);

    // Tìm chi tiết phiếu theo ID và quyền sở hữu của Shop
    Optional<WarrantyRequest> findByIdAndShopIdAndIsDeleted(Integer id, Integer shopId, Long isDeleted);

    // Chặn khách spam tạo 10 phiếu hoàn tiền cho cùng 1 món hàng
    boolean existsByOrderDetailIdAndStatusNotInAndIsDeleted(Integer orderDetailId, java.util.List<com.project.shopapp.domains.after_sales.enums.WarrantyStatus> statuses, Long isDeleted);
}