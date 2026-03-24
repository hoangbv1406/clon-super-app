package com.project.shopapp.domains.finance.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.finance.dto.response.WalletResponse;
import com.project.shopapp.domains.finance.service.WalletService;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/finance/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final SecurityUtils securityUtils;

    @GetMapping("/mine")
    @PreAuthorize("hasAnyRole('VENDOR', 'USER')")
    public ResponseEntity<ResponseObject<WalletResponse>> getMyWallet() {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(walletService.getMyWallet(userId)));
    }

    @PatchMapping("/users/{userId}/lock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<Void>> lockUserWallet(
            @PathVariable Integer userId,
            @RequestParam String reason) {
        Integer adminId = securityUtils.getLoggedInUserId();
        walletService.lockWallet(adminId, userId, reason);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã khóa Ví của user"));
    }
}