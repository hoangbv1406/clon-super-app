// --- api/WalletInternalApi.java ---
package com.project.shopapp.domains.finance.api;
import java.math.BigDecimal;

public interface WalletInternalApi {
    void createWalletForNewUser(Integer userId);

    // Khi đơn hàng Giao thành công (Gọi từ OrderShopService)
    void holdFundsForShop(Integer shopOwnerId, BigDecimal amount, String referenceCode);

    // Rã đông tiền (Gọi từ Job quét Đơn hàng đã qua 3 ngày không khiếu nại)
    void releaseHeldFunds(Integer shopOwnerId, BigDecimal amount, String referenceCode);

    // Rút tiền (Gọi từ WithdrawalService)
    void deductFunds(Integer userId, BigDecimal amount, String referenceCode);
}