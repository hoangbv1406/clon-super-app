// --- api/impl/WalletInternalApiImpl.java ---
package com.project.shopapp.domains.finance.api.impl;

import com.project.shopapp.domains.finance.api.WalletInternalApi;
import com.project.shopapp.domains.finance.entity.Wallet;
import com.project.shopapp.domains.finance.repository.WalletRepository;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletInternalApiImpl implements WalletInternalApi {

    private final WalletRepository walletRepo;
    // private final WalletTransactionInternalApi transactionLogApi; // Để ghi log vào bảng wallet_transactions (bài sau)

    @Override
    @Transactional
    public void createWalletForNewUser(Integer userId) {
        if (walletRepo.findByUserId(userId).isEmpty()) {
            walletRepo.save(Wallet.builder().userId(userId).build());
        }
    }

    @Override
    @Transactional
    public void holdFundsForShop(Integer shopOwnerId, BigDecimal amount, String referenceCode) {
        Wallet wallet = getWalletOrThrow(shopOwnerId);
        int rows = walletRepo.addFrozenBalance(wallet.getId(), amount);
        if (rows == 0) throw new ConflictException("Ví đang bị khóa hoặc lỗi hệ thống");

        // TODO: Gọi transactionLogApi để ghi log "HOLD"
    }

    @Override
    @Transactional
    public void releaseHeldFunds(Integer shopOwnerId, BigDecimal amount, String referenceCode) {
        Wallet wallet = getWalletOrThrow(shopOwnerId);
        int rows = walletRepo.unfreezeBalance(wallet.getId(), amount);
        if (rows == 0) throw new ConflictException("Không đủ số dư đóng băng hoặc ví bị khóa");

        // TODO: Gọi transactionLogApi để ghi log "RELEASE"
    }

    @Override
    @Transactional
    public void deductFunds(Integer userId, BigDecimal amount, String referenceCode) {
        Wallet wallet = getWalletOrThrow(userId);
        int rows = walletRepo.deductAvailableBalance(wallet.getId(), amount);
        if (rows == 0) throw new ConflictException("Số dư khả dụng không đủ hoặc ví đang bị khóa");

        // TODO: Gọi transactionLogApi để ghi log "WITHDRAW"
    }

    private Wallet getWalletOrThrow(Integer userId) {
        return walletRepo.findByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException("Ví không tồn tại"));
    }
}