package com.project.shopapp.domains.chat.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.chat.dto.request.MessageSendRequest;
import com.project.shopapp.domains.chat.dto.response.MessageResponse;
import com.project.shopapp.domains.chat.service.ChatMessageService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat/messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService messageService;
    private final SecurityUtils securityUtils;

    // Gửi tin nhắn qua REST (Hỗ trợ fallback khi WebSockets đứt)
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<MessageResponse>> sendMessage(
            @Valid @RequestBody MessageSendRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                messageService.sendMessage(userId, request), "Đã gửi"
        ));
    }

    // Load lịch sử (Scroll Up trên App)
    @GetMapping("/rooms/{roomId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<PageResponse<MessageResponse>>> getHistory(
            @PathVariable Integer roomId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                messageService.getHistory(userId, roomId, page, size)
        ));
    }

    // Thu hồi tin nhắn
    @PatchMapping("/{messageId}/recall")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseObject<Void>> recallMessage(@PathVariable Long messageId) {
        Integer userId = securityUtils.getLoggedInUserId();
        messageService.recallMessage(userId, messageId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã thu hồi tin nhắn"));
    }
}