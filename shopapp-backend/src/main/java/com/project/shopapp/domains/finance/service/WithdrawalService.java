// --- service/WithdrawalService.java ---
package com.project.shopapp.domains.finance.service;
import com.project.shopapp.domains.finance.dto.request.WithdrawalCreateRequest;
import com.project.shopapp.domains.finance.dto.request.WithdrawalProcessRequest;
import com.project.shopapp.domains.finance.dto.response.WithdrawalResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.time.LocalDateTime;

public interface WithdrawalService {
    WithdrawalResponse requestWithdrawal(Integer userId, WithdrawalCreateRequest request);
    PageResponse<WithdrawalResponse> getMyWithdrawals(Integer userId, int page, int size);

    // Dành cho Kế Toán
    PageResponse<WithdrawalResponse> searchWithdrawals(String status, Integer userId, LocalDateTime from, LocalDateTime to, int page, int size);
    WithdrawalResponse processWithdrawal(Integer adminId, Integer requestId, WithdrawalProcessRequest request);
}