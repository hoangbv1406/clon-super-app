// --- service/impl/ChatParticipantServiceImpl.java ---
package com.project.shopapp.domains.chat.service.impl;

import com.project.shopapp.domains.chat.dto.request.ParticipantSettingsRequest;
import com.project.shopapp.domains.chat.entity.ChatParticipant;
import com.project.shopapp.domains.chat.event.MessageReadEvent;
import com.project.shopapp.domains.chat.repository.ChatParticipantRepository;
import com.project.shopapp.domains.chat.service.ChatParticipantService;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatParticipantServiceImpl implements ChatParticipantService {

    private final ChatParticipantRepository participantRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void updateSettings(Integer userId, Integer roomId, ParticipantSettingsRequest request) {
        ChatParticipant participant = getParticipantOrThrow(roomId, userId);
        if (request.getIsMuted() != null) {
            participant.setIsMuted(request.getIsMuted());
            participantRepo.save(participant);
        }
    }

    @Override
    @Transactional
    public void hideChatRoom(Integer userId, Integer roomId) {
        ChatParticipant participant = getParticipantOrThrow(roomId, userId);
        participant.setIsDeleted(System.currentTimeMillis()); // Ẩn khỏi list chat của User này
        participantRepo.save(participant);
    }

    @Override
    @Transactional
    public void markAsRead(Integer userId, Integer roomId, Long messageId) {
        participantRepo.updateLastReadMessage(roomId, userId, messageId);
        // Bắn tín hiệu sang WS để máy đối phương hiện chữ "Đã xem"
        eventPublisher.publishEvent(new MessageReadEvent(roomId, userId, messageId));
    }

    private ChatParticipant getParticipantOrThrow(Integer roomId, Integer userId) {
        return participantRepo.findByRoomIdAndUserIdAndIsDeleted(roomId, userId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Bạn không phải thành viên của phòng chat này"));
    }
}