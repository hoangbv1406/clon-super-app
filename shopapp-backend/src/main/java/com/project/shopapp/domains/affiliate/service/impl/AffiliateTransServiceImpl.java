// --- service/impl/AffiliateTransServiceImpl.java ---
package com.project.shopapp.domains.affiliate.service.impl;

import com.project.shopapp.domains.affiliate.dto.nested.AffiliateDashboardDto;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateTransResponse;
import com.project.shopapp.domains.affiliate.entity.AffiliateTransaction;
import com.project.shopapp.domains.affiliate.enums.AffiliateTransStatus;
import com.project.shopapp.domains.affiliate.mapper.AffiliateTransMapper;
import com.project.shopapp.domains.affiliate.repository.AffiliateTransactionRepository;
import com.project.shopapp.domains.affiliate.service.AffiliateTransService;
import com.project.shopapp.shared.base.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AffiliateTransServiceImpl implements AffiliateTransService {

    private final AffiliateTransactionRepository transRepo;
    private final AffiliateTransMapper transMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AffiliateTransResponse> getMyTransactions(Integer userId, int page, int size) {
        Page<AffiliateTransaction> pagedResult = transRepo.findByKocUserId(
                userId, PageRequest.of(page - 1, size, Sort.by("createdAt").descending()));
        return PageResponse.of(pagedResult.map(transMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public AffiliateDashboardDto getMyDashboard(Integer userId) {
        AffiliateDashboardDto dto = new AffiliateDashboardDto();
        dto.setTotalPending(transRepo.sumAmountByStatusAndUserId(userId, AffiliateTransStatus.PENDING));
        dto.setTotalApproved(transRepo.sumAmountByStatusAndUserId(userId, AffiliateTransStatus.APPROVED));
        dto.setTotalPaid(transRepo.sumAmountByStatusAndUserId(userId, AffiliateTransStatus.PAID));
        return dto;
    }
}