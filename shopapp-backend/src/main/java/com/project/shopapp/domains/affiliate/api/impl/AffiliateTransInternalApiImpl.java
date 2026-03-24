
// --- api/impl/AffiliateTransInternalApiImpl.java ---
package com.project.shopapp.domains.affiliate.api.impl;

import com.project.shopapp.domains.affiliate.api.AffiliateTransInternalApi;
import com.project.shopapp.domains.affiliate.entity.AffiliateTransaction;
import com.project.shopapp.domains.affiliate.enums.AffiliateTransStatus;
import com.project.shopapp.domains.affiliate.repository.AffiliateTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class AffiliateTransInternalApiImpl implements AffiliateTransInternalApi {

    private final AffiliateTransactionRepository transRepo;

    @Override
    @Transactional
    public void recordPendingCommission(Integer affiliateLinkId, Integer orderShopId, BigDecimal commissionAmount) {
        try {
            AffiliateTransaction trans = AffiliateTransaction.builder()
                    .affiliateLinkId(affiliateLinkId)
                    .orderShopId(orderShopId)
                    .amount(commissionAmount)
                    .status(AffiliateTransStatus.PENDING)
                    .createdBy(0) // System
                    .build();
            transRepo.save(trans);
            log.info("Đã ghi nhận Hoa hồng PENDING: {} VNĐ cho Đơn {}", commissionAmount, orderShopId);
        } catch (DataIntegrityViolationException e) {
            // CATCH UNIQUE KEY: ux_aff_trans_link_order -> Tránh cộng tiền đúp nếu Event bắn 2 lần
            log.warn("Hoa hồng cho đơn {} đã tồn tại, bỏ qua.", orderShopId);
        }
    }

    @Override
    @Transactional
    public void reverseCommissionDueToReturn(Integer orderShopId) {
        transRepo.findByOrderShopId(orderShopId).ifPresent(tx -> {
            tx.setStatus(AffiliateTransStatus.REVERSED);
            transRepo.save(tx);
            log.info("Đã REVERSED (Hủy) hoa hồng của Đơn {} do khách Trả Hàng.", orderShopId);
        });
    }

    @Override
    @Transactional
    public void cancelCommissionDueToFailedDelivery(Integer orderShopId) {
        transRepo.findByOrderShopId(orderShopId).ifPresent(tx -> {
            tx.setStatus(AffiliateTransStatus.CANCELLED);
            transRepo.save(tx);
            log.info("Đã CANCELLED hoa hồng của Đơn {} do Giao Thất Bại.", orderShopId);
        });
    }
}