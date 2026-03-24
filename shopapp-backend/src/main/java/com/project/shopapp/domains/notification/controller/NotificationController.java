package com.project.shopapp.domains.notification.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.notification.dto.nested.UnreadCountDto;
import com.project.shopapp.domains.notification.dto.response.NotificationResponse;
import com.project.shopapp.domains.notification.service.NotificationService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notiService;
    private final SecurityUtils securityUtils;

    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<PageResponse<NotificationResponse>>> getMyNotifications(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean isRead,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                notiService.getMyNotifications(userId, type, isRead, page, size)
        ));
    }

    @GetMapping("/mine/unread-count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<UnreadCountDto>> getUnreadCount() {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(notiService.getUnreadCount(userId)));
    }

    @PatchMapping("/{id}/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> markAsRead(@PathVariable Long id) {
        Integer userId = securityUtils.getLoggedInUserId();
        notiService.markAsRead(userId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã đọc"));
    }

    @PatchMapping("/read-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> markAllAsRead() {
        Integer userId = securityUtils.getLoggedInUserId();
        notiService.markAllAsRead(userId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã đánh dấu tất cả là đã đọc"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> deleteNotification(@PathVariable Long id) {
        Integer userId = securityUtils.getLoggedInUserId();
        notiService.deleteNotification(userId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa thông báo"));
    }
}