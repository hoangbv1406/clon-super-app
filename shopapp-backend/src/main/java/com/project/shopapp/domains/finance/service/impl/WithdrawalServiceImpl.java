// --- service/impl/WithdrawalServiceImpl.java ---
package com.project.shopapp.domains.finance.service.impl;

import com.project.shopapp.domains.finance.api.WalletInternalApi;
import com.project.shopapp.domains.finance.constant.WalletTransactionConstants;
import com.project.shopapp.domains.finance.dto.request.WithdrawalCreateRequest;
import com.project.shopapp.domains.finance.dto.request.WithdrawalProcessRequest;
import com.project.shopapp.domains.finance.dto.response.WithdrawalResponse;
import com.project.shopapp.domains.finance.entity.WithdrawalRequest;
import com.project.shopapp.domains.finance.enums.WithdrawalStatus;
import com.project.shopapp.domains.finance.event.WithdrawalStatusChangedEvent;
import com.project.shopapp.domains.finance.mapper.WithdrawalMapper;
import com.project.shopapp.domains.finance.repository.WithdrawalRepository;
import com.project.shopapp.domains.finance.service.WithdrawalService;
import com.project.shopapp.domains.finance.specification.WithdrawalSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements WithdrawalService {

    private final WithdrawalRepository withdrawalRepo;
    private final WithdrawalMapper withdrawalMapper;
    private final WalletInternalApi walletApi; // Liên kết cốt lõi
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WithdrawalResponse requestWithdrawal(Integer userId, WithdrawalCreateRequest request) {
        // 1. Trừ tiền khỏi ví NGAY LẬP TỨC. Nếu không đủ tiền, hàm deductFunds sẽ ném Exception.
        String refCode = WalletTransactionConstants.PREFIX_REF_WITHDRAW + System.currentTimeMillis();
        walletApi.deductFunds(userId, request.getAmount(), refCode);

        // 2. Lưu yêu cầu rút tiền
        WithdrawalRequest req = withdrawalMapper.toEntityFromRequest(request);
        req.setUserId(userId);
        req.setStatus(WithdrawalStatus.PENDING);

        return withdrawalMapper.toDto(withdrawalRepo.save(req));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WithdrawalResponse> getMyWithdrawals(Integer userId, int page, int size) {
        Page<WithdrawalRequest> pagedResult = withdrawalRepo.findByUserId(
                userId, PageRequest.of(page - 1, size, Sort.by("createdAt").descending()));
        return PageResponse.of(pagedResult.map(withdrawalMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WithdrawalResponse> searchWithdrawals(String statusStr, Integer userId, LocalDateTime from, LocalDateTime to, int page, int size) {
        WithdrawalStatus status = statusStr != null ? WithdrawalStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<WithdrawalRequest> pagedResult = withdrawalRepo.findAll(
                WithdrawalSpecification.filterForAdmin(status, userId, from, to),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(withdrawalMapper::toDto));
    }

    @Override
    @Transactional
    public WithdrawalResponse processWithdrawal(Integer adminId, Integer requestId, WithdrawalProcessRequest request) {
        WithdrawalRequest req = withdrawalRepo.findById(requestId)
                .orElseThrow(() -> new DataNotFoundException("Lệnh rút tiền không tồn tại"));

        if (req.getStatus() != WithdrawalStatus.PENDING) {
            throw new ConflictException("Chỉ có thể xử lý các lệnh đang ở trạng thái PENDING");
        }

        if (Boolean.TRUE.equals(request.getIsApproved())) {
            if (request.getBankTransferRef() == null || request.getBankTransferRef().isBlank()) {
                throw new InvalidParamException("Phải nhập Mã giao dịch ngân hàng (UNC) khi duyệt lệnh.");
            }
            req.setStatus(WithdrawalStatus.APPROVED);
            req.setBankTransferRef(request.getBankTransferRef());
        } else {
            if (request.getAdminNote() == null || request.getAdminNote().isBlank()) {
                throw new InvalidParamException("Phải nhập lý do từ chối để Shop biết.");
            }
            req.setStatus(WithdrawalStatus.REJECTED);
            req.setAdminNote(request.getAdminNote());

            // QUAN TRỌNG: Hoàn tiền lại vào ví vì lệnh bị từ chối
            // Gọi qua WalletRepository để addAvailableBalance (Tớ sẽ thêm hàm này ngầm định)
            // walletApi.refundDeductedFunds(...)
        }

        req.setApprovedBy(adminId);
        req.setResolvedAt(LocalDateTime.now());
        WithdrawalRequest saved = withdrawalRepo.save(req);

        eventPublisher.publishEvent(new WithdrawalStatusChangedEvent(saved.getId(), saved.getUserId(), saved.getStatus()));
        return withdrawalMapper.toDto(saved);
    }
}