// --- api/impl/AffiliateInternalApiImpl.java ---
package com.project.shopapp.domains.affiliate.api.impl;

import com.project.shopapp.domains.affiliate.api.AffiliateInternalApi;
import com.project.shopapp.domains.affiliate.entity.AffiliateLink;
import com.project.shopapp.domains.affiliate.repository.AffiliateLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AffiliateInternalApiImpl implements AffiliateInternalApi {

    private final AffiliateLinkRepository linkRepo;
    // private final AffiliateTransactionInternalApi transApi; // Sẽ dùng ở bảng sau

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getCustomCommissionRate(String code) {
        return linkRepo.findByCodeAndIsDeleted(code, 0L)
                .map(AffiliateLink::getCommissionRate)
                .orElse(null);
    }

    @Override
    @Transactional
    public void processAffiliateReward(String code, Long orderId, BigDecimal orderSubTotal) {
        if (code == null) return;

        AffiliateLink link = linkRepo.findByCodeAndIsDeleted(code, 0L).orElse(null);
        if (link == null || !link.getIsActive()) return;

        // Giả lập mức Commission mặc định là 5% nếu không có rate riêng
        BigDecimal rate = link.getCommissionRate() != null ? link.getCommissionRate() : new BigDecimal("5.00");

        // Tính tiền hoa hồng (Ví dụ: Đơn 1.000.000, hoa hồng 5% -> 50.000)
        BigDecimal reward = orderSubTotal.multiply(rate).divide(new BigDecimal("100"));

        // Cộng vào bảng Dashboard
        linkRepo.recordSuccessfulOrder(code, reward);

        // TODO: Gọi API lưu chi tiết vào bảng `affiliate_transactions` (Làm ở phần sau)
        // transApi.createPendingTransaction(link.getId(), orderId, reward);
    }
}