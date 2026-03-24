package com.project.shopapp.domains.chat.specification;

import com.project.shopapp.domains.chat.entity.ChatMessage;
import org.springframework.data.jpa.domain.Specification;

public class ChatMessageSpecification {
    // Tính năng: "Tìm kiếm trong cuộc trò chuyện"
    public static Specification<ChatMessage> searchInRoom(Integer roomId, String keyword) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("roomId"), roomId),
                cb.equal(root.get("isDeleted"), false),
                cb.like(root.get("content"), "%" + keyword + "%")
        );
    }
}