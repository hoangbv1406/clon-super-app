// --- api/WalletTransactionInternalApi.java ---
package com.project.shopapp.domains.finance.api;
import com.project.shopapp.domains.finance.enums.WalletTransType;
import java.math.BigDecimal;

public interface WalletTransactionInternalApi {
    void recordLedgerEntry(Integer walletId, BigDecimal amount, WalletTransType type,
                           String desc, Long refOrderId, String refCode,
                           BigDecimal balanceBefore, BigDecimal balanceAfter);
}