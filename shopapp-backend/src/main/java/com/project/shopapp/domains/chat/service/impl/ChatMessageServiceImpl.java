// --- service/impl/ChatMessageServiceImpl.java ---
package com.project.shopapp.domains.chat.service.impl;

import com.project.shopapp.domains.chat.api.ChatParticipantInternalApi;
import com.project.shopapp.domains.chat.api.ChatRoomInternalApi;
import com.project.shopapp.domains.chat.dto.request.MessageSendRequest;
import com.project.shopapp.domains.chat.dto.response.MessageResponse;
import com.project.shopapp.domains.chat.entity.ChatMessage;
import com.project.shopapp.domains.chat.enums.MessageType;
import com.project.shopapp.domains.chat.event.MessageBroadcastEvent;
import com.project.shopapp.domains.chat.mapper.ChatMessageMapper;
import com.project.shopapp.domains.chat.repository.ChatMessageRepository;
import com.project.shopapp.domains.chat.service.ChatMessageService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository messageRepo;
    private final ChatMessageMapper messageMapper;
    private final ChatParticipantInternalApi participantApi;
    private final ChatRoomInternalApi roomApi;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public MessageResponse sendMessage(Integer senderId, MessageSendRequest request) {
        // 1. Kiểm tra quyền chat (Bị Kick thì cấm nhắn)
        if (!participantApi.isUserInRoom(request.getRoomId(), senderId)) {
            throw new ConflictException("Bạn không có quyền nhắn tin trong phòng này");
        }

        // 2. Lưu Database
        ChatMessage msg = messageMapper.toEntityFromRequest(request);
        msg.setSenderId(senderId);
        msg.setType(MessageType.valueOf(request.getType().toUpperCase()));
        ChatMessage saved = messageRepo.save(msg);

        // 3. Đánh thức phòng chat (Ai xóa chat rồi thì hiện lại)
        participantApi.wakeUpParticipants(request.getRoomId());

        // 4. Cập nhật snippet để đẩy phòng lên TOP trong danh sách Inbox
        String snippet = msg.getType() == MessageType.TEXT ? msg.getContent() : "[" + msg.getType().name() + "]";
        roomApi.updateLastMessage(request.getRoomId(), snippet);

        // 5. Bắn Event cho Websocket & Firebase Notification
        MessageResponse responseDto = messageMapper.toDto(saved);
        eventPublisher.publishEvent(new MessageBroadcastEvent(responseDto, request.getRoomId(), senderId));

        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MessageResponse> getHistory(Integer userId, Integer roomId, int page, int size) {
        if (!participantApi.isUserInRoom(roomId, userId)) throw new ConflictException("Không có quyền truy cập");

        Page<ChatMessage> pagedResult = messageRepo.findByRoomIdOrderByCreatedAtDesc(
                roomId, PageRequest.of(page - 1, size)
        );
        return PageResponse.of(pagedResult.map(msg -> {
            MessageResponse dto = messageMapper.toDto(msg);
            if (Boolean.TRUE.equals(msg.getIsDeleted())) {
                dto.setContent("Tin nhắn đã bị thu hồi"); // Che nội dung nếu bị thu hồi
                dto.setAttachmentUrl(null);
            }
            return dto;
        }));
    }

    @Override
    @Transactional
    public void recallMessage(Integer senderId, Long messageId) {
        ChatMessage msg = messageRepo.findByIdAndSenderId(messageId, senderId)
                .orElseThrow(() -> new DataNotFoundException("Tin nhắn không tồn tại hoặc bạn không phải người gửi"));

        msg.setIsDeleted(true);
        messageRepo.save(msg);

        // TODO: Bắn 1 Event WebSocket type=RECALL để Frontend của người kia xóa tin nhắn trên màn hình ngay lập tức
    }
}