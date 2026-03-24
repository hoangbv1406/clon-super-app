package com.project.shopapp.domains.inventory.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.inventory.dto.request.TransactionCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionResponse;
import com.project.shopapp.domains.inventory.service.InventoryTransactionService;
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
@RequestMapping("/api/v1/vendor/shops/{shopId}/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService txService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<PageResponse<TransactionResponse>>> getTransactions(
            @PathVariable Integer shopId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(ResponseObject.success(
                txService.searchTransactions(shopId, type, status, startDate, endDate, page, size)
        ));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<TransactionResponse>> createTransaction(
            @PathVariable Integer shopId,
            @Valid @RequestBody TransactionCreateRequest request) {

        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                txService.createManualTransaction(currentUserId, shopId, request),
                "Tạo phiếu kho thành công"
        ));
    }

    @PatchMapping("/{transactionId}/approve")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER')") // Chỉ Quản lý mới được duyệt phiếu
    public ResponseEntity<ResponseObject<TransactionResponse>> approveTransaction(
            @PathVariable Integer shopId,
            @PathVariable Long transactionId) {

        Integer currentUserId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                txService.approveTransaction(currentUserId, shopId, transactionId),
                "Duyệt phiếu kho thành công"
        ));
    }
}