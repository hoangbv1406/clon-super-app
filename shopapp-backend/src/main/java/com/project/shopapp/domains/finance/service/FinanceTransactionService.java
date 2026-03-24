// --- service/TransactionService.java ---
package com.project.shopapp.domains.finance.service;
import com.project.shopapp.domains.finance.dto.request.WebhookIPNRequest;
import com.project.shopapp.domains.finance.dto.response.TransactionResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface FinanceTransactionService {
    PageResponse<TransactionResponse> getTransactionsForAdmin(String orderCode, String status, String method, int page, int size);

    // IPN Handler (Lắng nghe webhook từ ngân hàng)
    String handleVnpayWebhook(WebhookIPNRequest request);
}