package com.project.shopapp.domains.identity.repository;

import com.project.shopapp.domains.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    // Chỉ tìm những user chưa bị xóa
    Optional<User> findByEmailAndIsDeleted(String email, Long isDeleted);

    boolean existsByEmailAndIsDeleted(String email, Long isDeleted);

    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempts = u.failedLoginAttempts + 1 WHERE u.email = :email")
    void incrementFailedLogins(String email);

    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempts = 0, u.lockedUntil = null WHERE u.email = :email")
    void resetFailedLogins(String email);

    @Query("SELECT u FROM User u WHERE (u.email = :username OR u.phoneNumber = :username) AND u.isDeleted = 0")
    Optional<User> findByEmailOrPhoneNumber(@Param("username") String username);
}