package com.project.shopapp.domains.finance.api.impl;

import com.project.shopapp.domains.finance.api.WalletTransactionInternalApi;
import com.project.shopapp.domains.finance.entity.WalletTransaction;
import com.project.shopapp.domains.finance.enums.WalletTransType;
import com.project.shopapp.domains.finance.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletTransactionInternalApiImpl implements WalletTransactionInternalApi {

    private final WalletTransactionRepository transRepo;

    // Propagation.MANDATORY: Bắt buộc phải chạy chung một luồng Transaction với hàm Cộng/Trừ tiền ở WalletInternalApi
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void recordLedgerEntry(Integer walletId, BigDecimal amount, WalletTransType type,
                                  String desc, Long refOrderId, String refCode,
                                  BigDecimal balanceBefore, BigDecimal balanceAfter) {
        try {
            WalletTransaction entry = WalletTransaction.builder()
                    .walletId(walletId)
                    .amount(amount)
                    .type(type)
                    .description(desc)
                    .refOrderId(refOrderId)
                    .referenceCode(refCode)
                    .balanceBefore(balanceBefore)
                    .balanceAfter(balanceAfter)
                    .createdBy(0) // System default
                    .build();
            transRepo.save(entry);
            log.info("Đã ghi nhận Sổ cái cho Ví {}: [{}] {} VNĐ. Ref: {}", walletId, type, amount, refCode);
        } catch (Exception e) {
            // Lỗi ở đây (ví dụ trùng Reference Code) sẽ làm CẢ QUÁ TRÌNH CỘNG TIỀN VÍ BỊ ROLLBACK.
            // Điều này cực kỳ an toàn!
            log.error("Lỗi khi ghi Sổ cái Kế toán: {}", e.getMessage());
            throw e;
        }
    }
}