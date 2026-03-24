package com.project.shopapp.domains.inventory.service.impl;

import com.project.shopapp.domains.catalog.api.ProductInternalApi; // Giả lập Facade bên Catalog
import com.project.shopapp.domains.inventory.dto.request.TransactionDetailCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionDetailResponse;
import com.project.shopapp.domains.inventory.entity.InventoryTransaction;
import com.project.shopapp.domains.inventory.entity.InventoryTransactionDetail;
import com.project.shopapp.domains.inventory.enums.TransactionStatus;
import com.project.shopapp.domains.inventory.enums.TransactionType;
import com.project.shopapp.domains.inventory.mapper.TransactionDetailMapper;
import com.project.shopapp.domains.inventory.repository.InventoryTransactionDetailRepository;
import com.project.shopapp.domains.inventory.repository.InventoryTransactionRepository;
import com.project.shopapp.domains.inventory.service.TransactionDetailService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final InventoryTransactionDetailRepository detailRepo;
    private final InventoryTransactionRepository txRepo;
    private final TransactionDetailMapper detailMapper;
    // private final ProductInternalApi productApi;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDetailResponse> getDetailsByTransaction(Long transactionId) {
        return detailRepo.findByTransactionId(transactionId)
                .stream().map(detailMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TransactionDetailResponse> addDetailsToTransaction(Long transactionId, Integer shopId, List<TransactionDetailCreateRequest> requests) {
        InventoryTransaction tx = txRepo.findById(transactionId)
                .filter(t -> t.getShopId().equals(shopId))
                .orElseThrow(() -> new DataNotFoundException("Phiếu kho không tồn tại"));

        if (tx.getStatus() != TransactionStatus.PENDING) {
            throw new ConflictException("Không thể sửa đổi dòng chi tiết của Phiếu đã Duyệt hoặc Hủy.");
        }

        List<InventoryTransactionDetail> detailsToSave = new ArrayList<>();
        BigDecimal additionalTotalValue = BigDecimal.ZERO;

        for (TransactionDetailCreateRequest req : requests) {
            // NGHIỆP VỤ ERP: Validation logic (+/-)
            if (tx.getType() == TransactionType.INBOUND || tx.getType() == TransactionType.RETURN) {
                if (req.getQuantityChanged() < 0) throw new ConflictException("Phiếu Nhập phải có Số lượng > 0");
            } else if (tx.getType() == TransactionType.OUTBOUND) {
                if (req.getQuantityChanged() > 0) throw new ConflictException("Phiếu Xuất phải có Số lượng < 0 (Âm)");
            }

            // NOTE: stockBefore và stockAfter tạm thời để = 0.
            // Chừng nào Admin bấm hàm approveTransaction() (ở bài trước), hệ thống mới thực sự đi khóa Tồn kho
            // và UPDATE lại 2 cột snapshot này để chốt sổ!

            InventoryTransactionDetail detail = detailMapper.toEntityFromRequest(req);
            detail.setTransactionId(transactionId);
            detailsToSave.add(detail);

            // Cộng dồn tổng tiền phiếu
            BigDecimal lineValue = req.getUnitCost().multiply(new BigDecimal(Math.abs(req.getQuantityChanged())));
            additionalTotalValue = additionalTotalValue.add(lineValue);
        }

        List<InventoryTransactionDetail> saved = detailRepo.saveAll(detailsToSave);

        // Cập nhật lại Total Value ở bảng Header
        tx.setTotalValue(tx.getTotalValue().add(additionalTotalValue));
        txRepo.save(tx);

        return saved.stream().map(detailMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeDetail(Long transactionId, Integer shopId, Long detailId) {
        InventoryTransaction tx = txRepo.findById(transactionId)
                .filter(t -> t.getShopId().equals(shopId))
                .orElseThrow(() -> new DataNotFoundException("Phiếu kho không tồn tại"));

        if (tx.getStatus() != TransactionStatus.PENDING) {
            throw new ConflictException("Không thể xóa dòng chi tiết của Phiếu đã chốt.");
        }

        InventoryTransactionDetail detail = detailRepo.findById(detailId).orElseThrow();
        if (!detail.getTransactionId().equals(transactionId)) {
            throw new ConflictException("Dữ liệu không khớp.");
        }

        // Trừ lại tiền ở Header
        BigDecimal lineValue = detail.getUnitCost().multiply(new BigDecimal(Math.abs(detail.getQuantityChanged())));
        tx.setTotalValue(tx.getTotalValue().subtract(lineValue));
        txRepo.save(tx);

        detailRepo.delete(detail); // Bảng này xóa cứng vì phiếu PENDING chỉ là bản nháp
    }
}