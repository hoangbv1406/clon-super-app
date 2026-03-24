package com.project.shopapp.domains.pos.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.pos.dto.request.PosSessionCloseRequest;
import com.project.shopapp.domains.pos.dto.request.PosSessionOpenRequest;
import com.project.shopapp.domains.pos.dto.response.PosSessionResponse;
import com.project.shopapp.domains.pos.service.PosSessionService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor/shops/{shopId}/pos-sessions")
@RequiredArgsConstructor
public class PosSessionController {

    private final PosSessionService sessionService;
    private final SecurityUtils securityUtils;

    @PostMapping("/open")
    @PreAuthorize("hasAnyRole('VENDOR', 'STAFF')") // Chủ shop hoặc nhân viên đều được mở két
    public ResponseEntity<ResponseObject<PosSessionResponse>> openSession(
            @PathVariable Integer shopId,
            @Valid @RequestBody PosSessionOpenRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                sessionService.openSession(userId, shopId, request), "Đã mở ca làm việc thành công"
        ));
    }

    @PostMapping("/close")
    @PreAuthorize("hasAnyRole('VENDOR', 'STAFF')")
    public ResponseEntity<ResponseObject<PosSessionResponse>> closeSession(
            @PathVariable Integer shopId,
            @Valid @RequestBody PosSessionCloseRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                sessionService.closeSession(userId, shopId, request), "Đã đóng ca làm việc (Chốt két)"
        ));
    }
}