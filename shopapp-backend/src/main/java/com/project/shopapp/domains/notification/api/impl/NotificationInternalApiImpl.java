// --- api/impl/NotificationInternalApiImpl.java ---
package com.project.shopapp.domains.notification.api.impl;

import com.project.shopapp.domains.notification.api.NotificationInternalApi;
import com.project.shopapp.domains.notification.entity.Notification;
import com.project.shopapp.domains.notification.enums.NotificationType;
import com.project.shopapp.domains.notification.event.NotificationPushedEvent;
import com.project.shopapp.domains.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationInternalApiImpl implements NotificationInternalApi {

    private final NotificationRepository notiRepo;
    private final ApplicationEventPublisher eventPublisher;

    // Yêu cầu chạy trong 1 luồng Tx mới (REQUIRES_NEW) để dù Đơn hàng bị lỗi Rollback, 
    // Thông báo lỗi vẫn có thể được đẩy đi độc lập. Hoặc ngược lại, tạo Noti lỗi ko làm chết Order.
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pushOrderUpdate(Integer userId, String orderCode, String statusVi, String deepLink) {
        String title = "Cập nhật đơn hàng " + orderCode;
        String body = "Đơn hàng của bạn đã chuyển sang trạng thái: " + statusVi;
        saveAndPush(userId, title, body, NotificationType.ORDER, orderCode, null, deepLink);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pushSystemAlert(Integer userId, String title, String body) {
        saveAndPush(userId, title, body, NotificationType.SYSTEM, null, null, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pushPromotion(Integer userId, String title, String body, String imageUrl, String deepLink) {
        saveAndPush(userId, title, body, NotificationType.PROMOTION, null, imageUrl, deepLink);
    }

    private void saveAndPush(Integer userId, String title, String body, NotificationType type, String refId, String imgUrl, String deepLink) {
        Notification noti = Notification.builder()
                .userId(userId)
                .title(title)
                .body(body)
                .type(type)
                .referenceId(refId)
                .imageUrl(imgUrl)
                .deepLink(deepLink)
                .isRead(false)
                .build();

        Notification saved = notiRepo.save(noti);

        // Bắn Event để Firebase/WebSocket nhận được và push đi real-time
        eventPublisher.publishEvent(new NotificationPushedEvent(saved));
    }
}