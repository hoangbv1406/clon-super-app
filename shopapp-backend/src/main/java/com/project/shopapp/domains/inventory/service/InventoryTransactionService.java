package com.project.shopapp.domains.inventory.service;
import com.project.shopapp.domains.inventory.dto.request.TransactionCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.time.LocalDateTime;

public interface InventoryTransactionService {
    PageResponse<TransactionResponse> searchTransactions(Integer shopId, String type, String status, LocalDateTime start, LocalDateTime end, int page, int size);
    TransactionResponse createManualTransaction(Integer currentUserId, Integer shopId, TransactionCreateRequest request);
    TransactionResponse approveTransaction(Integer currentUserId, Integer shopId, Long transactionId);
}