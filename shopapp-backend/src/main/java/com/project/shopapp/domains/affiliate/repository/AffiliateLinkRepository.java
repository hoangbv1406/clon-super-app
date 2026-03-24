package com.project.shopapp.domains.affiliate.repository;

import com.project.shopapp.domains.affiliate.entity.AffiliateLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AffiliateLinkRepository extends JpaRepository<AffiliateLink, Integer> {

    Optional<AffiliateLink> findByCodeAndIsDeleted(String code, Long isDeleted);

    boolean existsByCodeAndIsDeleted(String code, Long isDeleted);

    // Tránh KOC tạo 100 link rác cho cùng 1 sản phẩm
    Optional<AffiliateLink> findByUserIdAndProductIdAndIsDeleted(Integer userId, Integer productId, Long isDeleted);

    Page<AffiliateLink> findByUserIdAndIsDeleted(Integer userId, Long isDeleted, Pageable pageable);

    // Tăng click bằng Native Query siêu tốc (Bỏ qua Hibernate Cache)
    @Modifying
    @Query(value = "UPDATE affiliate_links SET clicks = clicks + 1 WHERE code = :code AND is_active = 1 AND is_deleted = 0", nativeQuery = true)
    void incrementClick(String code);

    // Cập nhật thống kê khi đơn hàng thành công
    @Modifying
    @Query("UPDATE AffiliateLink a SET a.ordersCount = a.ordersCount + 1, a.totalEarnings = a.totalEarnings + :earnedAmount WHERE a.code = :code")
    void recordSuccessfulOrder(String code, BigDecimal earnedAmount);
}