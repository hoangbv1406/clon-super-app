// --- service/impl/CouponUsageServiceImpl.java ---
package com.project.shopapp.domains.marketing.service.impl;

import com.project.shopapp.domains.marketing.dto.response.CouponUsageResponse;
import com.project.shopapp.domains.marketing.entity.CouponUsage;
import com.project.shopapp.domains.marketing.enums.CouponUsageStatus;
import com.project.shopapp.domains.marketing.mapper.CouponUsageMapper;
import com.project.shopapp.domains.marketing.repository.CouponUsageRepository;
import com.project.shopapp.domains.marketing.service.CouponUsageService;
import com.project.shopapp.domains.marketing.specification.CouponUsageSpecification;
import com.project.shopapp.shared.base.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponUsageServiceImpl implements CouponUsageService {

    private final CouponUsageRepository usageRepo;
    private final CouponUsageMapper usageMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CouponUsageResponse> getUsagesForAdmin(Integer couponId, Integer userId, String statusStr, int page, int size) {
        CouponUsageStatus status = statusStr != null ? CouponUsageStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<CouponUsage> pagedResult = usageRepo.findAll(
                CouponUsageSpecification.searchForAdmin(couponId, userId, status),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(usageMapper::toDto));
    }
}