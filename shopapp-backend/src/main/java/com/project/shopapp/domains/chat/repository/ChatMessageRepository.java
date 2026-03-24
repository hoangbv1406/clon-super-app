package com.project.shopapp.domains.chat.repository;

import com.project.shopapp.domains.chat.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, JpaSpecificationExecutor<ChatMessage> {

    // Lấy lịch sử chat, Partitioning giúp query này cực nhanh
    Page<ChatMessage> findByRoomIdOrderByCreatedAtDesc(Integer roomId, Pageable pageable);

    // Dùng cho Thu hồi tin nhắn
    Optional<ChatMessage> findByIdAndSenderId(Long id, Integer senderId);
}