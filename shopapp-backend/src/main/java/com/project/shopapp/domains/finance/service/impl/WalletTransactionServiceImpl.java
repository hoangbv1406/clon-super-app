// --- service/impl/WalletTransactionServiceImpl.java ---
package com.project.shopapp.domains.finance.service.impl;

import com.project.shopapp.domains.finance.dto.response.WalletTransResponse;
import com.project.shopapp.domains.finance.entity.Wallet;
import com.project.shopapp.domains.finance.entity.WalletTransaction;
import com.project.shopapp.domains.finance.enums.WalletTransType;
import com.project.shopapp.domains.finance.mapper.WalletTransactionMapper;
import com.project.shopapp.domains.finance.repository.WalletRepository;
import com.project.shopapp.domains.finance.repository.WalletTransactionRepository;
import com.project.shopapp.domains.finance.service.WalletTransactionService;
import com.project.shopapp.domains.finance.specification.WalletTransSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository transRepo;
    private final WalletRepository walletRepo;
    private final WalletTransactionMapper transMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WalletTransResponse> getMyWalletHistory(Integer userId, int page, int size) {
        Wallet wallet = walletRepo.findByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException("Bạn chưa có ví"));

        Page<WalletTransaction> pagedResult = transRepo.findByWalletId(
                wallet.getId(),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(transMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WalletTransResponse> filterWalletHistoryForAdmin(Integer walletId, String typeStr, LocalDateTime from, LocalDateTime to, int page, int size) {
        WalletTransType type = typeStr != null ? WalletTransType.valueOf(typeStr.toUpperCase()) : null;
        Page<WalletTransaction> pagedResult = transRepo.findAll(
                WalletTransSpecification.filterLedger(walletId, type, from, to),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(transMapper::toDto));
    }
}