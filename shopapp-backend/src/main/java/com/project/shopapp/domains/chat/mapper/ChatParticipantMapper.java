package com.project.shopapp.domains.chat.mapper;

import com.project.shopapp.domains.chat.dto.response.ParticipantResponse;
import com.project.shopapp.domains.chat.entity.ChatParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatParticipantMapper {

    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.profileImage", target = "avatarUrl")
    ParticipantResponse toDto(ChatParticipant entity);
}