package com.project.shopapp.domains.affiliate.repository;

import com.project.shopapp.domains.affiliate.entity.AffiliateTransaction;
import com.project.shopapp.domains.affiliate.enums.AffiliateTransStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AffiliateTransactionRepository extends JpaRepository<AffiliateTransaction, Long>, JpaSpecificationExecutor<AffiliateTransaction> {

    // Tìm kiếm các giao dịch của 1 User (thông qua link)
    @Query("SELECT t FROM AffiliateTransaction t JOIN t.affiliateLink l WHERE l.userId = :userId")
    Page<AffiliateTransaction> findByKocUserId(Integer userId, Pageable pageable);

    // Tính tổng tiền theo trạng thái của 1 KOC
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM AffiliateTransaction t JOIN t.affiliateLink l WHERE l.userId = :userId AND t.status = :status")
    BigDecimal sumAmountByStatusAndUserId(Integer userId, AffiliateTransStatus status);

    // Lấy danh sách giao dịch cần được tự động duyệt (Đã PENDING quá X ngày)
    // Thực tế sẽ join với bảng order_shop để xem ngày DELIVERED đã qua 7 ngày chưa
    @Query("SELECT t FROM AffiliateTransaction t JOIN t.orderShop os " +
            "WHERE t.status = 'PENDING' AND os.status = 'DELIVERED' " +
            "AND os.updatedAt < :thresholdDate")
    List<AffiliateTransaction> findTransactionsToAutoApprove(java.time.LocalDateTime thresholdDate);

    // Tìm hoa hồng của 1 đơn hàng cụ thể để Hủy (nếu khách bom hàng)
    Optional<AffiliateTransaction> findByOrderShopId(Integer orderShopId);

    // Đổi trạng thái hàng loạt lúc Kế toán trả tiền
    @Modifying
    @Query("UPDATE AffiliateTransaction t SET t.status = 'PAID', t.payoutDate = CURRENT_TIMESTAMP WHERE t.id IN :ids")
    void markAsPaidBatch(List<Long> ids);
}