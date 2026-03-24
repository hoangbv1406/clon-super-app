// --- api/impl/PaymentInternalApiImpl.java ---
package com.project.shopapp.domains.finance.api.impl;

import com.project.shopapp.domains.finance.api.PaymentInternalApi;
import com.project.shopapp.domains.finance.entity.Transaction;
import com.project.shopapp.domains.finance.enums.GatewayTransactionStatus;
import com.project.shopapp.domains.finance.enums.GatewayTransactionType;
import com.project.shopapp.domains.finance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentInternalApiImpl implements PaymentInternalApi {

    private final TransactionRepository transactionRepo;

    @Override
    @Transactional
    public String initPaymentForOrder(Long orderId, BigDecimal amount, String method) {
        // Tạo giao dịch PENDING
        Transaction tx = Transaction.builder()
                .orderId(orderId)
                .amount(amount)
                .paymentMethod(method)
                .type(GatewayTransactionType.PAYMENT)
                .status(GatewayTransactionStatus.PENDING)
                .build();

        Transaction saved = transactionRepo.save(tx);

        // TODO: Factory Pattern gọi sang class VNPAY_Service hoặc MOMO_Service 
        // để truyền saved.getId() và amount lên sinh URL. 
        // Ở đây tớ mockup trả về URL.
        return "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_TxnRef=" + saved.getId() + "&vnp_Amount=" + (amount.longValue() * 100);
    }

    @Override
    @Transactional
    public boolean processRefund(Long orderId, Integer orderShopId, BigDecimal refundAmount, String reason) {
        // Tìm giao dịch gốc
        Transaction originalTx = transactionRepo.findByOrderIdAndType(orderId, GatewayTransactionType.PAYMENT)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giao dịch gốc để hoàn tiền"));

        if (originalTx.getStatus() != GatewayTransactionStatus.SUCCESS) {
            throw new IllegalStateException("Giao dịch gốc chưa thành công, không thể hoàn tiền.");
        }

        // Tạo giao dịch REFUND mới, trỏ parent về gốc
        Transaction refundTx = Transaction.builder()
                .orderId(orderId)
                .orderShopId(orderShopId)
                .parentTransactionId(originalTx.getId())
                .amount(refundAmount)
                .paymentMethod(originalTx.getPaymentMethod())
                .type(GatewayTransactionType.REFUND)
                .status(GatewayTransactionStatus.PENDING)
                .errorMessage("Reason: " + reason)
                .build();

        transactionRepo.save(refundTx);

        // TODO: Gọi API REST của Ngân hàng để thực hiện Refund (VNPAY Refund API).
        // Ngân hàng xử lý xong sẽ call Webhook về, lúc đó ta mới Update trạng thái refundTx thành SUCCESS.
        return true;
    }
}