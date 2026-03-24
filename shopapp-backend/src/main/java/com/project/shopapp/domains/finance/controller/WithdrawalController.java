package com.project.shopapp.domains.finance.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.finance.dto.request.WithdrawalCreateRequest;
import com.project.shopapp.domains.finance.dto.request.WithdrawalProcessRequest;
import com.project.shopapp.domains.finance.dto.response.WithdrawalResponse;
import com.project.shopapp.domains.finance.service.WithdrawalService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/finance/withdrawals")
@RequiredArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;
    private final SecurityUtils securityUtils;

    // VENDOR: Đặt lệnh rút tiền
    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'USER')")
    public ResponseEntity<ResponseObject<WithdrawalResponse>> createRequest(@Valid @RequestBody WithdrawalCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                withdrawalService.requestWithdrawal(userId, request), "Đã gửi yêu cầu rút tiền"
        ));
    }

    // VENDOR: Xem lịch sử rút tiền của mình
    @GetMapping("/mine")
    @PreAuthorize("hasAnyRole('VENDOR', 'USER')")
    public ResponseEntity<ResponseObject<PageResponse<WithdrawalResponse>>> getMyRequests(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(withdrawalService.getMyWithdrawals(userId, page, size)));
    }

    // KẾ TOÁN/ADMIN: Xem tất cả lệnh
    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<ResponseObject<PageResponse<WithdrawalResponse>>> searchRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                withdrawalService.searchWithdrawals(status, userId, from, to, page, size)
        ));
    }

    // KẾ TOÁN/ADMIN: Duyệt hoặc Từ chối lệnh
    @PatchMapping("/admin/{id}/process")
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<ResponseObject<WithdrawalResponse>> processRequest(
            @PathVariable Integer id,
            @Valid @RequestBody WithdrawalProcessRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                withdrawalService.processWithdrawal(adminId, id, request), "Đã xử lý yêu cầu rút tiền"
        ));
    }
}