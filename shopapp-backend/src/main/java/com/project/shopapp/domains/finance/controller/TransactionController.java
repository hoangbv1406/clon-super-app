// --- controller/TransactionController.java ---
package com.project.shopapp.domains.finance.controller;

import com.project.shopapp.domains.finance.dto.response.TransactionResponse;
import com.project.shopapp.domains.finance.service.FinanceTransactionService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/finance/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final FinanceTransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public ResponseEntity<ResponseObject<PageResponse<TransactionResponse>>> getTransactions(
            @RequestParam(required = false) String orderCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String method,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                transactionService.getTransactionsForAdmin(orderCode, status, method, page, size)
        ));
    }
}