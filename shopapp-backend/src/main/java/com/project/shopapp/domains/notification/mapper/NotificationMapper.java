package com.project.shopapp.domains.notification.mapper;

import com.project.shopapp.domains.notification.dto.request.NotificationSendRequest;
import com.project.shopapp.domains.notification.dto.response.NotificationResponse;
import com.project.shopapp.domains.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponse toDto(Notification entity);

    @Mapping(target = "type", ignore = true) // Set manually in Service
    Notification toEntityFromRequest(NotificationSendRequest request);
}