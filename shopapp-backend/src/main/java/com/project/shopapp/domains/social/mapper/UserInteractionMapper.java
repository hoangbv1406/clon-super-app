package com.project.shopapp.domains.social.mapper;

import com.project.shopapp.domains.social.dto.request.InteractionLogRequest;
import com.project.shopapp.domains.social.entity.UserInteraction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserInteractionMapper {
    @Mapping(target = "actionType", ignore = true) // Map ở service
    UserInteraction toEntityFromRequest(InteractionLogRequest request);
}