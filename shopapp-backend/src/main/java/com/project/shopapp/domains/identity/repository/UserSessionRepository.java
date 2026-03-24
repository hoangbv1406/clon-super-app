package com.project.shopapp.domains.identity.repository;

import com.project.shopapp.domains.identity.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long>, JpaSpecificationExecutor<UserSession> {

    Optional<UserSession> findByRefreshTokenHash(String hash);

    List<UserSession> findByUserIdAndIsRevokedFalseAndExpiresAtAfter(Integer userId, LocalDateTime now);

    // Force logout toàn bộ các thiết bị của 1 User (Dùng khi đổi mật khẩu hoặc phát hiện bị hack)
    @Modifying
    @Query("UPDATE UserSession u SET u.isRevoked = true WHERE u.userId = :userId AND u.isRevoked = false")
    void revokeAllUserSessions(Integer userId);

    // Xóa các session đã hết hạn để giải phóng Database (Gọi từ CronJob)
    @Modifying
    @Query("DELETE FROM UserSession u WHERE u.expiresAt < :now")
    int deleteAllExpiredSince(LocalDateTime now);
}