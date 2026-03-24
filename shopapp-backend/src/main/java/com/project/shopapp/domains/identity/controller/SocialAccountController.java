package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.dto.request.SocialAuthRequest;
import com.project.shopapp.domains.identity.dto.response.SocialAccountResponse;
import com.project.shopapp.domains.identity.dto.response.TokenResponse;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.identity.service.SocialAccountService;
import com.project.shopapp.shared.base.ResponseObject;
import com.project.shopapp.shared.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity/social")
@RequiredArgsConstructor
public class SocialAccountController {

    private final SocialAccountService socialService;
    private final SecurityUtils securityUtils;

    // PUBLIC API: Đăng nhập bằng Google/Facebook
    @PostMapping("/login")
    public ResponseEntity<ResponseObject<TokenResponse>> socialLogin(
            @Valid @RequestBody SocialAuthRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = WebUtils.getClientIp(httpRequest);
        String userAgent = WebUtils.getUserAgent(httpRequest);
        String deviceId = WebUtils.getDeviceId(httpRequest);

        return ResponseEntity.ok(ResponseObject.success(
                socialService.loginOrRegisterWithSocial(request, ipAddress, userAgent, deviceId),
                "Đăng nhập thành công"
        ));
    }

    // AUTH REQUIRED: Lấy danh sách tài khoản liên kết
    @GetMapping("/linked")
    public ResponseEntity<ResponseObject<List<SocialAccountResponse>>> getLinkedAccounts() {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(socialService.getLinkedAccounts(userId)));
    }

    // AUTH REQUIRED: Gỡ liên kết
    @DeleteMapping("/unlink/{provider}")
    public ResponseEntity<ResponseObject<Void>> unlinkAccount(@PathVariable String provider) {
        Integer userId = securityUtils.getLoggedInUserId();
        socialService.unlinkSocialAccount(userId, provider);
        return ResponseEntity.ok(ResponseObject.success(null, "Gỡ liên kết tài khoản thành công"));
    }
}