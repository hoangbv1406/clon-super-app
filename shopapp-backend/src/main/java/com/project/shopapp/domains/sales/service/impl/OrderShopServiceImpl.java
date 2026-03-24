// --- service/impl/OrderShopServiceImpl.java ---
package com.project.shopapp.domains.sales.service.impl;

import com.project.shopapp.domains.sales.dto.request.OrderShopFulfillmentRequest;
import com.project.shopapp.domains.sales.dto.response.OrderShopResponse;
import com.project.shopapp.domains.sales.entity.OrderShop;
import com.project.shopapp.domains.sales.enums.OrderShopStatus;
import com.project.shopapp.domains.sales.event.OrderShopDeliveredEvent;
import com.project.shopapp.domains.sales.mapper.OrderShopMapper;
import com.project.shopapp.domains.sales.repository.OrderShopRepository;
import com.project.shopapp.domains.sales.service.OrderShopService;
import com.project.shopapp.domains.sales.specification.OrderShopSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderShopServiceImpl implements OrderShopService {

    private final OrderShopRepository orderShopRepo;
    private final OrderShopMapper orderShopMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderShopResponse> getVendorOrders(Integer shopId, String statusStr, String code, int page, int size) {
        OrderShopStatus status = statusStr != null ? OrderShopStatus.valueOf(statusStr.toUpperCase()) : null;
        Page<OrderShop> pagedResult = orderShopRepo.findAll(
                OrderShopSpecification.filterForVendor(shopId, status, code),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(orderShopMapper::toDto));
    }

    @Override
    @Transactional
    public OrderShopResponse updateFulfillment(Integer userId, Integer shopId, Integer orderShopId, OrderShopFulfillmentRequest request) {
        OrderShop order = orderShopRepo.findByIdAndShopIdAndIsDeleted(orderShopId, shopId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Kiện hàng không tồn tại"));

        if (order.getStatus() == OrderShopStatus.DELIVERED || order.getStatus() == OrderShopStatus.CANCELLED) {
            throw new ConflictException("Không thể cập nhật kiện hàng đã đóng hoặc đã hủy.");
        }

        if (request.getCarrierName() != null) order.setCarrierName(request.getCarrierName());
        if (request.getTrackingNumber() != null) order.setTrackingNumber(request.getTrackingNumber());
        if (request.getShopNote() != null) order.setShopNote(request.getShopNote());

        order.setStatus(OrderShopStatus.PROCESSING); // Có tracking code -> Đang đóng gói
        order.setUpdatedBy(userId);

        return orderShopMapper.toDto(orderShopRepo.save(order));
    }

    @Override
    @Transactional
    public void markAsShipped(Integer userId, Integer shopId, Integer orderShopId) {
        OrderShop order = orderShopRepo.findByIdAndShopIdAndIsDeleted(orderShopId, shopId, 0L).orElseThrow();
        if (order.getTrackingNumber() == null) throw new ConflictException("Phải nhập Mã vận đơn trước khi xuất kho.");

        order.setStatus(OrderShopStatus.SHIPPED);
        order.setShippingDate(LocalDateTime.now());
        order.setUpdatedBy(userId);
        orderShopRepo.save(order);
    }

    @Override
    @Transactional
    public void markAsDelivered(Integer userId, Integer shopId, Integer orderShopId) {
        // Thực tế hàm này sẽ được gọi bằng Webhook từ GHTK/GHN chứ Vendor ít khi tự bấm
        OrderShop order = orderShopRepo.findByIdAndShopIdAndIsDeleted(orderShopId, shopId, 0L).orElseThrow();
        order.setStatus(OrderShopStatus.DELIVERED);
        order.setUpdatedBy(userId);

        OrderShop saved = orderShopRepo.save(order);

        // BẮN EVENT CHỐT TIỀN
        eventPublisher.publishEvent(new OrderShopDeliveredEvent(
                saved.getId(), saved.getShopId(), saved.getParentOrderId(), saved.getShopIncome()
        ));
    }
}