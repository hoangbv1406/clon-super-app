package com.project.shopapp.domains.chat.entity;

import com.project.shopapp.domains.chat.enums.ChatRoomType;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.shared.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ChatRoom extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name; // Thường dùng cho GROUP, nếu PRIVATE có thể để null hoặc gen tự động

    @Column(name = "shop_id")
    private Integer shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private Shop shop;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Builder.Default
    private ChatRoomType type = ChatRoomType.PRIVATE;

    @Column(name = "last_message", columnDefinition = "TEXT")
    private String lastMessage;

    // Cột updated_at đã được BaseSoftDeleteEntity cover, nó sẽ tự động cập nhật
    // mỗi khi ta lưu lại ChatRoom (nhờ tính năng @LastModifiedDate).
}