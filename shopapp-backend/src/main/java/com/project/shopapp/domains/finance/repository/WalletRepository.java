package com.project.shopapp.domains.finance.repository;

import com.project.shopapp.domains.finance.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Optional<Wallet> findByUserId(Integer userId);

    // 1. Thêm tiền vào Tài khoản đóng băng (Khi đơn hàng vừa Giao Thành Công)
    @Modifying
    @Query("UPDATE Wallet w SET w.frozenBalance = w.frozenBalance + :amount, w.version = w.version + 1 " +
            "WHERE w.id = :walletId AND w.status = 'ACTIVE'")
    int addFrozenBalance(Integer walletId, BigDecimal amount);

    // 2. Rã đông: Trừ ở frozen, cộng vào balance (Khi hết thời gian đổi trả 3 ngày)
    @Modifying
    @Query("UPDATE Wallet w SET w.frozenBalance = w.frozenBalance - :amount, w.balance = w.balance + :amount, w.version = w.version + 1 " +
            "WHERE w.id = :walletId AND w.frozenBalance >= :amount AND w.status = 'ACTIVE'")
    int unfreezeBalance(Integer walletId, BigDecimal amount);

    // 3. Khấu trừ trực tiếp (Rút tiền, Thanh toán, Hủy đơn)
    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance - :amount, w.version = w.version + 1 " +
            "WHERE w.id = :walletId AND w.balance >= :amount AND w.status = 'ACTIVE'")
    int deductAvailableBalance(Integer walletId, BigDecimal amount);

    // 4. Nạp tiền trực tiếp (Admin tặng thưởng)
    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance + :amount, w.version = w.version + 1 " +
            "WHERE w.id = :walletId AND w.status = 'ACTIVE'")
    int addAvailableBalance(Integer walletId, BigDecimal amount);
}