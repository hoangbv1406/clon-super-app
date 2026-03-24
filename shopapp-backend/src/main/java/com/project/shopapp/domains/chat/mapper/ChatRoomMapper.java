package com.project.shopapp.domains.chat.mapper;

import com.project.shopapp.domains.chat.dto.response.ChatRoomResponse;
import com.project.shopapp.domains.chat.entity.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {

    @Mapping(target = "avatarUrl", ignore = true) // Map ở Service tùy theo người gọi là ai
    @Mapping(target = "unreadCount", ignore = true) // Map qua bảng Message
    ChatRoomResponse toDto(ChatRoom entity);
}