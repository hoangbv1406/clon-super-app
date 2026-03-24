// --- api/impl/ChatParticipantInternalApiImpl.java ---
package com.project.shopapp.domains.chat.api.impl;

import com.project.shopapp.domains.chat.api.ChatParticipantInternalApi;
import com.project.shopapp.domains.chat.entity.ChatParticipant;
import com.project.shopapp.domains.chat.enums.ParticipantRole;
import com.project.shopapp.domains.chat.repository.ChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatParticipantInternalApiImpl implements ChatParticipantInternalApi {

    private final ChatParticipantRepository participantRepo;

    @Override
    @Transactional
    public void addInitialParticipants(Integer roomId, Integer customerUserId, Integer shopOwnerId) {
        ChatParticipant customer = ChatParticipant.builder()
                .roomId(roomId)
                .userId(customerUserId)
                .role(ParticipantRole.MEMBER)
                .build();

        ChatParticipant shop = ChatParticipant.builder()
                .roomId(roomId)
                .userId(shopOwnerId)
                .role(ParticipantRole.ADMIN) // Shop là Admin
                .build();

        participantRepo.saveAll(List.of(customer, shop));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserInRoom(Integer roomId, Integer userId) {
        return participantRepo.findByRoomIdAndUserIdAndIsDeleted(roomId, userId, 0L).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getLastReadMessageId(Integer roomId, Integer userId) {
        return participantRepo.findByRoomIdAndUserIdAndIsDeleted(roomId, userId, 0L)
                .map(ChatParticipant::getLastReadMessageId)
                .orElse(0L);
    }

    @Override
    @Transactional
    public void wakeUpParticipants(Integer roomId) {
        participantRepo.restoreDeletedParticipantsByRoom(roomId);
    }
}