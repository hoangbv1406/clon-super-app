package com.project.shopapp.domains.chat.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.chat.dto.request.ChatRoomCreateRequest;
import com.project.shopapp.domains.chat.dto.response.ChatRoomResponse;
import com.project.shopapp.domains.chat.service.ChatRoomService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService roomService;
    private final SecurityUtils securityUtils;

    // USER: Bấm nút Chat trên trang Sản phẩm
    @PostMapping("/initiate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<ChatRoomResponse>> initiateChat(
            @Valid @RequestBody ChatRoomCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                roomService.initiateChatWithShop(userId, request), "Phòng chat sẵn sàng"
        ));
    }

    // USER: Mở danh sách tin nhắn của khách
    @GetMapping("/inbox/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<PageResponse<ChatRoomResponse>>> getUserInbox(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(roomService.getUserInbox(userId, page, size)));
    }

    // VENDOR: Mở danh sách tin nhắn của Shop
    @GetMapping("/inbox/vendor/shops/{shopId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'CSKH')")
    public ResponseEntity<ResponseObject<PageResponse<ChatRoomResponse>>> getVendorInbox(
            @PathVariable Integer shopId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        // TODO: Chặn IDOR - Đảm bảo current user là nhân viên của shopId này
        return ResponseEntity.ok(ResponseObject.success(roomService.getVendorInbox(shopId, page, size)));
    }
}