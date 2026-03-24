// --- api/impl/ChatRoomInternalApiImpl.java ---
package com.project.shopapp.domains.chat.api.impl;

import com.project.shopapp.domains.chat.api.ChatRoomInternalApi;
import com.project.shopapp.domains.chat.entity.ChatRoom;
import com.project.shopapp.domains.chat.enums.ChatRoomType;
import com.project.shopapp.domains.chat.event.ChatRoomCreatedEvent;
import com.project.shopapp.domains.chat.repository.ChatRoomRepository;
// import com.project.shopapp.domains.chat.repository.ChatParticipantRepository; // Dùng ở bài sau
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomInternalApiImpl implements ChatRoomInternalApi {

    private final ChatRoomRepository roomRepo;
    private final ApplicationEventPublisher eventPublisher;
    // private final ChatParticipantRepository participantRepo; // Sẽ inject khi code bảng participant

    @Override
    @Transactional
    public Integer getOrProvisionPrivateRoom(Integer userId, Integer shopId) {
        return roomRepo.findPrivateRoomByUserAndShop(userId, shopId)
                .map(ChatRoom::getId)
                .orElseGet(() -> {
                    // Tạo phòng mới
                    ChatRoom newRoom = ChatRoom.builder()
                            .shopId(shopId)
                            .type(ChatRoomType.PRIVATE)
                            .createdBy(userId)
                            .build();
                    ChatRoom savedRoom = roomRepo.save(newRoom);

                    // TODO: Tạo 2 dòng vào bảng `chat_participants` (1 cho User, 1 cho Shop Owner)
                    // participantRepo.save(...);

                    // Bắn event để Auto-bot gửi tin chào mừng
                    eventPublisher.publishEvent(new ChatRoomCreatedEvent(savedRoom.getId(), shopId, userId));

                    return savedRoom.getId();
                });
    }

    @Override
    @Transactional
    public void updateLastMessage(Integer roomId, String messageSnippet) {
        roomRepo.findById(roomId).ifPresent(room -> {
            room.setLastMessage(messageSnippet);
            // JPA @LastModifiedDate sẽ tự động cập nhật `updated_at` = NOW()
            roomRepo.save(room);
        });
    }
}