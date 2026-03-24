// --- service/NotificationService.java ---
package com.project.shopapp.domains.notification.service;
import com.project.shopapp.domains.notification.dto.nested.UnreadCountDto;
import com.project.shopapp.domains.notification.dto.response.NotificationResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface NotificationService {
    PageResponse<NotificationResponse> getMyNotifications(Integer userId, String type, Boolean isRead, int page, int size);
    UnreadCountDto getUnreadCount(Integer userId);
    void markAsRead(Integer userId, Long notiId);
    void markAllAsRead(Integer userId);
    void deleteNotification(Integer userId, Long notiId);
}