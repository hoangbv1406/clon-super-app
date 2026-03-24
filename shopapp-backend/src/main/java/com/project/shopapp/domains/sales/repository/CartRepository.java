package com.project.shopapp.domains.sales.repository;

import com.project.shopapp.domains.sales.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {

    // Tìm giỏ hàng theo User
    Optional<Cart> findByUserIdAndIsDeleted(Integer userId, Long isDeleted);

    // Tìm giỏ hàng theo Session ID (Dành cho Guest)
    Optional<Cart> findBySessionIdAndIsDeleted(String sessionId, Long isDeleted);

    // Dọn dẹp Giỏ hàng Vãng lai hết hạn (Tránh phình to DB)
    @Modifying
    @Query("UPDATE Cart c SET c.isDeleted = :nowEpoch WHERE c.userId IS NULL AND c.expiresAt < :now AND c.isDeleted = 0")
    int softDeleteExpiredGuestCarts(LocalDateTime now, Long nowEpoch);
}