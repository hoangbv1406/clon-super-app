package com.project.shopapp.domains.social.repository;

import com.project.shopapp.domains.social.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {

    // Tìm hành động Follow cá nhân
    Optional<UserFollow> findByFollowerIdAndFollowingUserIdAndIsDeleted(Integer followerId, Integer followingUserId, Long isDeleted);

    // Tìm hành động Follow Shop
    Optional<UserFollow> findByFollowerIdAndFollowingShopIdAndIsDeleted(Integer followerId, Integer followingShopId, Long isDeleted);

    // Đếm số người ĐANG THEO DÕI 1 Shop (Phục vụ API của Shop)
    long countByFollowingShopIdAndIsDeleted(Integer shopId, Long isDeleted);

    // Đếm số người ĐANG THEO DÕI 1 KOC (Phục vụ API Profile User)
    long countByFollowingUserIdAndIsDeleted(Integer userId, Long isDeleted);

    // Đếm số lượng người/shop MÀ USER NÀY đang theo dõi (Phục vụ limit chống bot)
    long countByFollowerIdAndIsDeleted(Integer followerId, Long isDeleted);

    // Danh sách "Đang theo dõi" của 1 User
    Page<UserFollow> findByFollowerIdAndIsDeleted(Integer followerId, Long isDeleted, Pageable pageable);

    // Bật lại Follow nếu User đã unfollow trước đó (Soft Delete Recovery)
    @Modifying
    @Query("UPDATE UserFollow f SET f.isDeleted = 0 WHERE f.id = :id")
    void restoreFollow(Integer id);
}