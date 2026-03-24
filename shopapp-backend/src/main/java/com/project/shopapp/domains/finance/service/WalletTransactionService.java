// --- service/WalletTransactionService.java ---
package com.project.shopapp.domains.finance.service;
import com.project.shopapp.domains.finance.dto.response.WalletTransResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.time.LocalDateTime;

public interface WalletTransactionService {
    PageResponse<WalletTransResponse> getMyWalletHistory(Integer userId, int page, int size);
    PageResponse<WalletTransResponse> filterWalletHistoryForAdmin(Integer walletId, String type, LocalDateTime from, LocalDateTime to, int page, int size);
}