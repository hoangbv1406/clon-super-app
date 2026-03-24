package com.project.shopapp.domains.inventory.service.impl;

import com.project.shopapp.domains.inventory.dto.request.TransactionCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionResponse;
import com.project.shopapp.domains.inventory.entity.InventoryTransaction;
import com.project.shopapp.domains.inventory.enums.ReferenceType;
import com.project.shopapp.domains.inventory.enums.TransactionStatus;
import com.project.shopapp.domains.inventory.enums.TransactionType;
import com.project.shopapp.domains.inventory.event.InventoryTransactionCompletedEvent;
import com.project.shopapp.domains.inventory.mapper.InventoryTransactionMapper;
import com.project.shopapp.domains.inventory.repository.InventoryTransactionRepository;
import com.project.shopapp.domains.inventory.service.InventoryTransactionService;
import com.project.shopapp.domains.inventory.specification.TransactionSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final InventoryTransactionRepository transactionRepo;
    private final InventoryTransactionMapper transactionMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TransactionResponse> searchTransactions(Integer shopId, String typeStr, String statusStr, LocalDateTime start, LocalDateTime end, int page, int size) {
        TransactionType type = typeStr != null ? TransactionType.valueOf(typeStr.toUpperCase()) : null;
        TransactionStatus status = statusStr != null ? TransactionStatus.valueOf(statusStr.toUpperCase()) : null;

        Page<InventoryTransaction> pagedResult = transactionRepo.findAll(
                TransactionSpecification.filter(shopId, type, status, start, end),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(transactionMapper::toDto));
    }

    @Override
    @Transactional
    public TransactionResponse createManualTransaction(Integer currentUserId, Integer shopId, TransactionCreateRequest request) {
        InventoryTransaction tx = transactionMapper.toEntityFromRequest(request);
        tx.setShopId(shopId);
        tx.setCreatedBy(currentUserId);

        if (request.getReferenceType() != null) {
            tx.setReferenceType(ReferenceType.valueOf(request.getReferenceType().toUpperCase()));
        } else {
            tx.setReferenceType(ReferenceType.MANUAL);
        }

        // Sinh mã phiếu: Tùy loại (IN/OUT) + yyyyMMdd + sequence
        String prefix = tx.getType() == TransactionType.INBOUND ? "IN" :
                (tx.getType() == TransactionType.OUTBOUND ? "OUT" : "ADJ");

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        long countToday = transactionRepo.countByShopIdAndCreatedAtBetween(shopId, startOfDay, endOfDay);

        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sequence = String.format("%04d", countToday + 1); // Đảm bảo độ dài 4 số: 0001

        tx.setTransactionCode(prefix + "-" + datePart + "-" + sequence);

        return transactionMapper.toDto(transactionRepo.save(tx));
    }

    @Override
    @Transactional
    public TransactionResponse approveTransaction(Integer currentUserId, Integer shopId, Long transactionId) {
        InventoryTransaction tx = transactionRepo.findById(transactionId)
                .filter(t -> t.getShopId().equals(shopId))
                .orElseThrow(() -> new DataNotFoundException("Phiếu kho không tồn tại"));

        if (tx.getStatus() != TransactionStatus.PENDING) {
            throw new InvalidParamException("Chỉ có thể duyệt phiếu đang ở trạng thái PENDING");
        }

        tx.setStatus(TransactionStatus.COMPLETED);
        tx.setUpdatedBy(currentUserId);
        InventoryTransaction saved = transactionRepo.save(tx);

        // BẮN EVENT KẾT CHUYỂN
        eventPublisher.publishEvent(new InventoryTransactionCompletedEvent(
                saved.getId(), saved.getShopId(), saved.getType(), saved.getTotalValue()
        ));

        return transactionMapper.toDto(saved);
    }
}