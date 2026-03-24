// --- service/impl/OrderDetailServiceImpl.java ---
package com.project.shopapp.domains.sales.service.impl;

import com.project.shopapp.domains.sales.dto.response.OrderDetailResponse;
import com.project.shopapp.domains.sales.entity.OrderDetail;
import com.project.shopapp.domains.sales.enums.OrderItemStatus;
import com.project.shopapp.domains.sales.event.PartialItemReturnRequestedEvent;
import com.project.shopapp.domains.sales.mapper.OrderDetailMapper;
import com.project.shopapp.domains.sales.repository.OrderDetailRepository;
import com.project.shopapp.domains.sales.service.OrderDetailService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository detailRepo;
    private final OrderDetailMapper detailMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailResponse> getDetailsByOrderId(Long orderId) {
        return detailRepo.findByOrderId(orderId).stream()
                .map(detailMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailResponse> getDetailsByOrderShopId(Integer orderShopId) {
        return detailRepo.findByOrderShopId(orderShopId).stream()
                .map(detailMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void requestPartialReturn(Integer userId, Integer detailId) {
        OrderDetail detail = detailRepo.findById(detailId).orElseThrow(() -> new DataNotFoundException("Sản phẩm không có trong đơn"));

        // TODO: Chặn IDOR - Phải check detail.getOrder().getUserId() == userId

        if (detail.getItemStatus() != OrderItemStatus.NORMAL) {
            throw new ConflictException("Sản phẩm này đã hoặc đang được yêu cầu đổi trả.");
        }

        detailRepo.updateItemStatus(detailId, OrderItemStatus.RETURN_REQUESTED);

        // Bắn Event để tạo Ticket bên bảng warranty_requests (Module CSKH)
        eventPublisher.publishEvent(new PartialItemReturnRequestedEvent(detailId, detail.getOrderId()));
    }
}