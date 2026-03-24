package com.project.shopapp.domains.finance.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.finance.dto.response.WalletTransResponse;
import com.project.shopapp.domains.finance.service.WalletTransactionService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/finance/wallet-transactions")
@RequiredArgsConstructor
public class WalletTransactionController {

    private final WalletTransactionService transService;
    private final SecurityUtils securityUtils;

    @GetMapping("/mine")
    @PreAuthorize("hasAnyRole('VENDOR', 'USER')")
    public ResponseEntity<ResponseObject<PageResponse<WalletTransResponse>>> getMyHistory(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(transService.getMyWalletHistory(userId, page, size)));
    }

    @GetMapping("/admin/wallets/{walletId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<PageResponse<WalletTransResponse>>> getHistoryForAdmin(
            @PathVariable Integer walletId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                transService.filterWalletHistoryForAdmin(walletId, type, from, to, page, size)
        ));
    }
}