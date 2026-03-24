package com.project.shopapp.domains.chat.mapper;

import com.project.shopapp.domains.chat.dto.request.MessageSendRequest;
import com.project.shopapp.domains.chat.dto.response.MessageResponse;
import com.project.shopapp.domains.chat.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    MessageResponse toDto(ChatMessage entity);

    @Mapping(target = "type", ignore = true) // Set manually trong service
    ChatMessage toEntityFromRequest(MessageSendRequest request);
}