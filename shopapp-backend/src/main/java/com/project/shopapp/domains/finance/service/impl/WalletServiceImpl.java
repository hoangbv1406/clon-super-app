// --- service/impl/WalletServiceImpl.java ---
package com.project.shopapp.domains.finance.service.impl;

import com.project.shopapp.domains.finance.dto.response.WalletResponse;
import com.project.shopapp.domains.finance.entity.Wallet;
import com.project.shopapp.domains.finance.enums.WalletStatus;
import com.project.shopapp.domains.finance.event.WalletLockedEvent;
import com.project.shopapp.domains.finance.mapper.WalletMapper;
import com.project.shopapp.domains.finance.repository.WalletRepository;
import com.project.shopapp.domains.finance.service.WalletService;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepo;
    private final WalletMapper walletMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public WalletResponse getMyWallet(Integer userId) {
        Wallet wallet = walletRepo.findByUserId(userId)
                .orElseThrow(() -> new DataNotFoundException("Bạn chưa được kích hoạt Ví. Vui lòng liên hệ CSKH."));
        return walletMapper.toDto(wallet);
    }

    @Override
    @Transactional
    public void lockWallet(Integer adminId, Integer targetUserId, String reason) {
        Wallet wallet = walletRepo.findByUserId(targetUserId)
                .orElseThrow(() -> new DataNotFoundException("Ví không tồn tại"));

        if (wallet.getStatus() != WalletStatus.LOCKED) {
            wallet.setStatus(WalletStatus.LOCKED);
            walletRepo.save(wallet);

            // Bắn event để Hủy các lệnh rút tiền đang chờ xử lý
            eventPublisher.publishEvent(new WalletLockedEvent(wallet.getId(), targetUserId, reason));
        }
    }
}