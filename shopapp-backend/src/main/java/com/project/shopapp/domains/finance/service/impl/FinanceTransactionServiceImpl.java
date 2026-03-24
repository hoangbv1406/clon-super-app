// --- service/impl/TransactionServiceImpl.java ---
package com.project.shopapp.domains.finance.service.impl;

import com.project.shopapp.domains.finance.dto.request.WebhookIPNRequest;
import com.project.shopapp.domains.finance.dto.response.TransactionResponse;
import com.project.shopapp.domains.finance.entity.Transaction;
import com.project.shopapp.domains.finance.enums.GatewayTransactionStatus;
import com.project.shopapp.domains.finance.event.PaymentFailedEvent;
import com.project.shopapp.domains.finance.event.PaymentSuccessEvent;
import com.project.shopapp.domains.finance.mapper.TransactionMapper;
import com.project.shopapp.domains.finance.repository.TransactionRepository;
import com.project.shopapp.domains.finance.service.FinanceTransactionService;
import com.project.shopapp.domains.finance.specification.TransactionSpecification;
import com.project.shopapp.shared.base.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinanceTransactionServiceImpl implements FinanceTransactionService {

    private final TransactionRepository transactionRepo;
    private final TransactionMapper transactionMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TransactionResponse> getTransactionsForAdmin(String orderCode, String statusStr, String method, int page, int size) {
        GatewayTransactionStatus status = statusStr != null ? GatewayTransactionStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<Transaction> pagedResult = transactionRepo.findAll(
                TransactionSpecification.searchForAdmin(orderCode, status, method),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(transactionMapper::toDto));
    }

    @Override
    @Transactional
    public String handleVnpayWebhook(WebhookIPNRequest request) {
        Map<String, Object> payload = request.getPayload();
        log.info("Nhận Webhook từ VNPAY: {}", payload);

        // 1. Lấy ID giao dịch (VNPAY trả về trong field vnp_TxnRef)
        String vnpTxnRef = (String) payload.get("vnp_TxnRef");
        Integer transactionId = Integer.parseInt(vnpTxnRef);

        Transaction tx = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giao dịch với ID: " + transactionId));

        // 2. Kiểm tra Idempotency (Nếu đã xử lý rồi thì bỏ qua, trả về OK cho ngân hàng)
        if (tx.getStatus() == GatewayTransactionStatus.SUCCESS || tx.getStatus() == GatewayTransactionStatus.FAILED) {
            return "{\"RspCode\":\"00\",\"Message\":\"Already Processed\"}"; // Chuẩn RspCode VNPAY
        }

        // TODO: Chỗ này BẮT BUỘC phải viết hàm check Signature Hash bằng SecretKey 
        // để đảm bảo request này đúng là của VNPAY gửi chứ ko phải hacker fake.

        // 3. Đọc kết quả giao dịch
        String responseCode = (String) payload.get("vnp_ResponseCode");
        String transactionNo = (String) payload.get("vnp_TransactionNo");

        tx.setGatewayCode(responseCode);
        tx.setTransactionCode(transactionNo);
        tx.setResponseJson(payload); // Lưu toàn bộ cục JSON để đối soát sau này

        if ("00".equals(responseCode)) {
            tx.setStatus(GatewayTransactionStatus.SUCCESS);
            transactionRepo.save(tx);
            // Bắn Event Đổi trạng thái Đơn hàng và Clear Giỏ hàng
            eventPublisher.publishEvent(new PaymentSuccessEvent(tx.getOrderId(), transactionNo));
        } else {
            tx.setStatus(GatewayTransactionStatus.FAILED);
            tx.setErrorMessage("Giao dịch bị hủy hoặc lỗi cổng thanh toán");
            transactionRepo.save(tx);
            // Bắn Event để Mở lại giỏ hàng cho khách thanh toán lại
            eventPublisher.publishEvent(new PaymentFailedEvent(tx.getOrderId()));
        }

        return "{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}";
    }
}