// --- service/impl/ChatRoomServiceImpl.java ---
package com.project.shopapp.domains.chat.service.impl;

import com.project.shopapp.domains.chat.api.ChatRoomInternalApi;
import com.project.shopapp.domains.chat.dto.request.ChatRoomCreateRequest;
import com.project.shopapp.domains.chat.dto.response.ChatRoomResponse;
import com.project.shopapp.domains.chat.entity.ChatRoom;
import com.project.shopapp.domains.chat.mapper.ChatRoomMapper;
import com.project.shopapp.domains.chat.repository.ChatRoomRepository;
import com.project.shopapp.domains.chat.service.ChatRoomService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository roomRepo;
    private final ChatRoomMapper roomMapper;
    private final ChatRoomInternalApi roomApi;
    // private final ShopInternalApi shopApi; // Lấy Avatar Shop
    // private final UserInternalApi userApi; // Lấy Avatar User

    @Override
    @Transactional
    public ChatRoomResponse initiateChatWithShop(Integer userId, ChatRoomCreateRequest request) {
        Integer roomId = roomApi.getOrProvisionPrivateRoom(userId, request.getShopId());
        ChatRoom room = roomRepo.findById(roomId).orElseThrow();

        ChatRoomResponse response = roomMapper.toDto(room);
        // TODO: Gắn tên Shop và Avatar Shop vào response.setName(), response.setAvatarUrl()
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ChatRoomResponse> getUserInbox(Integer userId, int page, int size) {
        Page<ChatRoom> pagedResult = roomRepo.findInboxByUserId(userId, PageRequest.of(page - 1, size));
        return PageResponse.of(pagedResult.map(room -> {
            ChatRoomResponse dto = roomMapper.toDto(room);
            // TODO: Gắn Avatar + Tên Shop để Khách thấy họ đang chat với ai
            // TODO: Gọi API đếm số tin nhắn chưa đọc (is_read = false) từ bảng chat_messages
            return dto;
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ChatRoomResponse> getVendorInbox(Integer shopId, int page, int size) {
        Page<ChatRoom> pagedResult = roomRepo.findInboxByShopId(shopId, PageRequest.of(page - 1, size));
        return PageResponse.of(pagedResult.map(room -> {
            ChatRoomResponse dto = roomMapper.toDto(room);
            // TODO: Gắn Avatar + Tên của User (Khách) để Chủ Shop biết đang tiếp ai
            return dto;
        }));
    }
}