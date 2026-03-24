package com.project.shopapp.domains.chat.repository;

import com.project.shopapp.domains.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    // Tìm phòng chat PRIVATE tồn tại giữa 1 User và 1 Shop
    @Query("SELECT r FROM ChatRoom r JOIN ChatParticipant p ON r.id = p.roomId " +
            "WHERE r.shopId = :shopId AND r.type = 'PRIVATE' AND p.userId = :userId AND r.isDeleted = 0")
    Optional<ChatRoom> findPrivateRoomByUserAndShop(Integer userId, Integer shopId);

    // Lấy danh sách Inbox cho Khách hàng (Sắp xếp theo tin nhắn mới nhất)
    @Query("SELECT r FROM ChatRoom r JOIN ChatParticipant p ON r.id = p.roomId " +
            "WHERE p.userId = :userId AND r.isDeleted = 0 ORDER BY r.updatedAt DESC")
    Page<ChatRoom> findInboxByUserId(Integer userId, Pageable pageable);

    // Lấy danh sách Inbox cho Chủ Shop
    @Query("SELECT r FROM ChatRoom r WHERE r.shopId = :shopId AND r.isDeleted = 0 ORDER BY r.updatedAt DESC")
    Page<ChatRoom> findInboxByShopId(Integer shopId, Pageable pageable);
}