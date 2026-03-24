// --- service/WalletService.java ---
package com.project.shopapp.domains.finance.service;
import com.project.shopapp.domains.finance.dto.response.WalletResponse;

public interface WalletService {
    WalletResponse getMyWallet(Integer userId);
    void lockWallet(Integer adminId, Integer userId, String reason);
}