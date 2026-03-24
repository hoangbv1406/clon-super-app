package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.identity.dto.request.CredentialRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.CredentialResponse;
import com.project.shopapp.domains.identity.service.UserCredentialService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity/credentials")
@RequiredArgsConstructor
public class UserCredentialController {

    private final UserCredentialService credentialService;
    private final SecurityUtils securityUtils; // Lấy ID user đang login

    @GetMapping("/my-devices")
    public ResponseEntity<ResponseObject<List<CredentialResponse>>> getMyCredentials() {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(credentialService.getUserCredentials(userId)));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject<CredentialResponse>> registerPasskey(@Valid @RequestBody CredentialRegisterRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                credentialService.registerCredential(userId, request), "Thêm thiết bị xác thực thành công"
        ));
    }

    @DeleteMapping("/{id}/revoke")
    public ResponseEntity<ResponseObject<Void>> revokePasskey(@PathVariable Integer id) {
        Integer userId = securityUtils.getLoggedInUserId();
        credentialService.revokeCredential(userId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã thu hồi thiết bị đăng nhập"));
    }
}