package com.project.shopapp.domains.inventory.api.impl;

import com.project.shopapp.domains.inventory.api.TransactionInternalApi;
import com.project.shopapp.domains.inventory.entity.InventoryTransaction;
import com.project.shopapp.domains.inventory.enums.ReferenceType;
import com.project.shopapp.domains.inventory.enums.TransactionStatus;
import com.project.shopapp.domains.inventory.enums.TransactionType;
import com.project.shopapp.domains.inventory.repository.InventoryTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionInternalApiImpl implements TransactionInternalApi {

    private final InventoryTransactionRepository transactionRepo;

    @Override
    @Transactional
    public Long autoCreateOutboundForOrder(Integer shopId, Long orderId, String note) {
        // Tự động sinh mã phiếu
        String txCode = "OUT-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0,4);

        InventoryTransaction tx = InventoryTransaction.builder()
                .shopId(shopId)
                .transactionCode(txCode)
                .type(TransactionType.OUTBOUND)
                .referenceType(ReferenceType.ORDER)
                .referenceId(orderId)
                .note("Hệ thống tự xuất kho cho đơn hàng: " + orderId)
                .status(TransactionStatus.COMPLETED) // Auto hoàn tất
                .createdBy(0) // System
                .build();

        return transactionRepo.save(tx).getId();
        // Cần kết hợp lưu bảng Details ở bước sau
    }
}