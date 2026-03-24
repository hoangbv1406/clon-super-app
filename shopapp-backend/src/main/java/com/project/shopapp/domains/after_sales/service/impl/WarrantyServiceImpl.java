// --- service/impl/WarrantyServiceImpl.java ---
package com.project.shopapp.domains.after_sales.service.impl;

import com.project.shopapp.domains.after_sales.dto.request.WarrantyCreateRequest;
import com.project.shopapp.domains.after_sales.dto.request.WarrantyProcessRequest;
import com.project.shopapp.domains.after_sales.dto.response.WarrantyResponse;
import com.project.shopapp.domains.after_sales.entity.WarrantyRequest;
import com.project.shopapp.domains.after_sales.enums.RequestType;
import com.project.shopapp.domains.after_sales.enums.WarrantyStatus;
import com.project.shopapp.domains.after_sales.event.ReturnCompletedEvent;
import com.project.shopapp.domains.after_sales.mapper.WarrantyMapper;
import com.project.shopapp.domains.after_sales.repository.WarrantyRequestRepository;
import com.project.shopapp.domains.after_sales.service.WarrantyService;
import com.project.shopapp.domains.after_sales.specification.WarrantySpecification;
import com.project.shopapp.domains.sales.entity.OrderDetail; // Giả sử có truy cập entity trực tiếp hoặc qua API
import com.project.shopapp.domains.sales.repository.OrderDetailRepository;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarrantyServiceImpl implements WarrantyService {

    private final WarrantyRequestRepository warrantyRepo;
    private final OrderDetailRepository orderDetailRepo; // Gọi chéo để check tính hợp lệ của Đơn hàng
    private final WarrantyMapper warrantyMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public WarrantyResponse createRequest(Integer userId, WarrantyCreateRequest request) {
        OrderDetail detail = orderDetailRepo.findById(request.getOrderDetailId())
                .orElseThrow(() -> new DataNotFoundException("Chi tiết đơn hàng không tồn tại"));

        // Check Quyền
        if (!detail.getOrder().getUserId().equals(userId)) {
            throw new ConflictException("Không có quyền khiếu nại món hàng này");
        }

        // Chặn Spam: 1 món hàng không thể bị Khiếu nại 2 lần khi phiếu trước chưa đóng
        boolean hasPending = warrantyRepo.existsByOrderDetailIdAndStatusNotInAndIsDeleted(
                detail.getId(), List.of(WarrantyStatus.COMPLETED, WarrantyStatus.REJECTED), 0L);
        if (hasPending) throw new ConflictException("Sản phẩm này đang có một phiếu khiếu nại đang được xử lý.");

        // Sinh mã phiếu
        long countToday = warrantyRepo.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
        String rmaCode = "RMA-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + "-" + String.format("%04d", countToday + 1);

        WarrantyRequest warranty = warrantyMapper.toEntityFromRequest(request);
        warranty.setUserId(userId);
        warranty.setShopId(detail.getOrderShopId()); // ĐỊNH TUYẾN VỀ ĐÚNG SHOP
        warranty.setRequestCode(rmaCode);
        warranty.setRequestType(RequestType.valueOf(request.getRequestType().toUpperCase()));
        warranty.setStatus(WarrantyStatus.PENDING);
        warranty.setCreatedBy(userId);

        return warrantyMapper.toDto(warrantyRepo.save(warranty));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WarrantyResponse> searchRequests(Integer shopId, Integer userId, String statusStr, String code, int page, int size) {
        WarrantyStatus status = statusStr != null ? WarrantyStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<WarrantyRequest> pagedResult = warrantyRepo.findAll(
                WarrantySpecification.filterRequests(shopId, userId, status, code),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(warrantyMapper::toDto));
    }

    @Override
    @Transactional
    public WarrantyResponse processRequest(Integer adminId, Integer shopId, Integer requestId, WarrantyProcessRequest request) {
        WarrantyRequest rma = warrantyRepo.findByIdAndShopIdAndIsDeleted(requestId, shopId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Phiếu khiếu nại không tồn tại"));

        if (rma.getStatus() == WarrantyStatus.COMPLETED || rma.getStatus() == WarrantyStatus.REJECTED) {
            throw new ConflictException("Không thể sửa phiếu đã Đóng.");
        }

        if (request.getStatus() != null) {
            WarrantyStatus newStatus = WarrantyStatus.valueOf(request.getStatus().toUpperCase());
            rma.setStatus(newStatus);

            // Nếu Shop chốt Hoàn Tiền (RETURN + COMPLETED)
            if (newStatus == WarrantyStatus.COMPLETED && rma.getRequestType() == RequestType.RETURN) {
                if (request.getRefundAmount() == null || request.getRefundAmount().compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvalidParamException("Phải nhập số tiền cần hoàn lại cho khách.");
                }
                rma.setRefundAmount(request.getRefundAmount());

                // BẮN EVENT HOÀN TIỀN CHO KHÁCH (Module Payment bắt được sẽ tạo Refund Tx)
                eventPublisher.publishEvent(new ReturnCompletedEvent(
                        rma.getOrderDetail().getOrderId(),
                        rma.getShopId(),
                        request.getRefundAmount(),
                        rma.getRequestCode()
                ));
            }
        }

        if (request.getAdminNote() != null) rma.setAdminNote(request.getAdminNote());
        rma.setUpdatedBy(adminId);

        return warrantyMapper.toDto(warrantyRepo.save(rma));
    }

    @Override
    @Transactional
    public void updateReturnTracking(Integer userId, Integer requestId, String trackingCode) {
        WarrantyRequest rma = warrantyRepo.findById(requestId).orElseThrow();
        if (!rma.getUserId().equals(userId)) throw new ConflictException("Không có quyền truy cập");
        if (rma.getStatus() != WarrantyStatus.APPROVED) throw new ConflictException("Chỉ được nhập mã vận đơn khi Shop đã Duyệt yêu cầu trả hàng");

        rma.setReturnTrackingCode(trackingCode);
        rma.setUpdatedBy(userId);
        warrantyRepo.save(rma);
    }
}