// --- service/impl/NotificationServiceImpl.java ---
package com.project.shopapp.domains.notification.service.impl;

import com.project.shopapp.domains.notification.dto.nested.UnreadCountDto;
import com.project.shopapp.domains.notification.dto.response.NotificationResponse;
import com.project.shopapp.domains.notification.entity.Notification;
import com.project.shopapp.domains.notification.enums.NotificationType;
import com.project.shopapp.domains.notification.mapper.NotificationMapper;
import com.project.shopapp.domains.notification.repository.NotificationRepository;
import com.project.shopapp.domains.notification.service.NotificationService;
import com.project.shopapp.domains.notification.specification.NotificationSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notiRepo;
    private final NotificationMapper notiMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<NotificationResponse> getMyNotifications(Integer userId, String typeStr, Boolean isRead, int page, int size) {
        NotificationType type = typeStr != null ? NotificationType.valueOf(typeStr.toUpperCase()) : null;
        Page<Notification> pagedResult = notiRepo.findAll(
                NotificationSpecification.filterForUser(userId, isRead, type),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(notiMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public UnreadCountDto getUnreadCount(Integer userId) {
        long count = notiRepo.countByUserIdAndIsReadFalseAndIsDeleted(userId, 0L);
        return new UnreadCountDto(count);
    }

    @Override
    @Transactional
    public void markAsRead(Integer userId, Long notiId) {
        Notification noti = notiRepo.findByIdAndUserIdAndIsDeleted(notiId, userId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Thông báo không tồn tại"));
        if (!noti.getIsRead()) {
            noti.setIsRead(true);
            notiRepo.save(noti);
        }
    }

    @Override
    @Transactional
    public void markAllAsRead(Integer userId) {
        notiRepo.markAllAsRead(userId);
    }

    @Override
    @Transactional
    public void deleteNotification(Integer userId, Long notiId) {
        Notification noti = notiRepo.findByIdAndUserIdAndIsDeleted(notiId, userId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Thông báo không tồn tại"));
        noti.setIsDeleted(System.currentTimeMillis());
        notiRepo.save(noti);
    }
}