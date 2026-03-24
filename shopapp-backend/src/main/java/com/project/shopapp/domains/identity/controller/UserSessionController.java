package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.dto.request.RefreshTokenRequest;
import com.project.shopapp.domains.identity.dto.response.TokenResponse;
import com.project.shopapp.domains.identity.dto.response.UserSessionResponse;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.identity.service.UserSessionService;
import com.project.shopapp.shared.base.ResponseObject;
import com.project.shopapp.shared.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity/sessions")
@RequiredArgsConstructor
public class UserSessionController {

    private final UserSessionService sessionService;
    private final SecurityUtils securityUtils;

    @PostMapping("/refresh")
    public ResponseEntity<ResponseObject<TokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = WebUtils.getClientIp(httpRequest);
        String userAgent = WebUtils.getUserAgent(httpRequest);

        return ResponseEntity.ok(ResponseObject.success(
                sessionService.refreshToken(request, ipAddress, userAgent),
                "Làm mới phiên đăng nhập thành công"
        ));
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseObject<List<UserSessionResponse>>> getActiveSessions(HttpServletRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        // Lấy token từ header Authorization để check session hiện tại
        String currentToken = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(ResponseObject.success(sessionService.getActiveSessions(userId, currentToken)));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<ResponseObject<Void>> revokeSession(@PathVariable Long sessionId) {
        Integer userId = securityUtils.getLoggedInUserId();
        sessionService.revokeSession(userId, sessionId);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã đăng xuất thiết bị thành công"));
    }
}