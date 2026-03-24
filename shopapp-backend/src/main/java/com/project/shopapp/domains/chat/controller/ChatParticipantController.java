package com.project.shopapp.domains.chat.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.chat.dto.request.ParticipantSettingsRequest;
import com.project.shopapp.domains.chat.service.ChatParticipantService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat/rooms/{roomId}/participants")
@RequiredArgsConstructor
public class ChatParticipantController {

    private final ChatParticipantService participantService;
    private final SecurityUtils securityUtils;

    @PatchMapping("/settings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> updateSettings(
            @PathVariable Integer roomId,
            @Valid @RequestBody ParticipantSettingsRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        participantService.updateSettings(userId, roomId, request);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã cập nhật cài đặt trò chuyện"));
    }

    @DeleteMapping("/hide")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> hideChatRoom(@PathVariable Integer roomId) {
        Integer userId = securityUtils.getLoggedInUserId();
        participantService.hideChatRoom(userId, roomId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã ẩn phòng chat"));
    }

    @PatchMapping("/messages/{messageId}/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> markMessageAsRead(
            @PathVariable Integer roomId,
            @PathVariable Long messageId) {
        Integer userId = securityUtils.getLoggedInUserId();
        participantService.markAsRead(userId, roomId, messageId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã gửi thông báo Đã xem"));
    }
}