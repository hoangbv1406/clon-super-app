package com.project.shopapp.domains.chat.entity;

import com.project.shopapp.domains.chat.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {

    // Ánh xạ Id. MySQL sẽ tự động quản lý kết hợp với created_at làm PK ở tầng Physical.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id", nullable = false, updatable = false)
    private Integer roomId;

    @Column(name = "sender_id", nullable = false, updatable = false)
    private Integer senderId;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Builder.Default
    private MessageType type = MessageType.TEXT;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attachment_url", columnDefinition = "json")
    private Map<String, Object> attachmentUrl; // JSON chứa { "url": "...", "productId": 123 }

    @Column(name = "is_read")
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false; // Thu hồi tin nhắn

    @Column(name = "reply_to_id")
    private Long replyToId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}