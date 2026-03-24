package com.project.shopapp.domains.chat.repository;

import com.project.shopapp.domains.chat.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Integer> {

    Optional<ChatParticipant> findByRoomIdAndUserIdAndIsDeleted(Integer roomId, Integer userId, Long isDeleted);

    List<ChatParticipant> findByRoomIdAndIsDeleted(Integer roomId, Long isDeleted);

    // Tính năng đánh dấu đã đọc (Seen) - Chỉ update khi tin nhắn mới có ID lớn hơn tin hiện tại
    @Modifying
    @Query("UPDATE ChatParticipant p SET p.lastReadMessageId = :messageId " +
            "WHERE p.roomId = :roomId AND p.userId = :userId AND p.isDeleted = 0 " +
            "AND (p.lastReadMessageId IS NULL OR p.lastReadMessageId < :messageId)")
    void updateLastReadMessage(Integer roomId, Integer userId, Long messageId);

    // Đánh thức những người đã "Ẩn" đoạn chat nếu có tin nhắn mới
    @Modifying
    @Query("UPDATE ChatParticipant p SET p.isDeleted = 0 WHERE p.roomId = :roomId AND p.isDeleted > 0")
    void restoreDeletedParticipantsByRoom(Integer roomId);
}