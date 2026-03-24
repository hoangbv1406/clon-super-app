// --- api/impl/OrderInternalApiImpl.java ---
package com.project.shopapp.domains.sales.api.impl;

import com.project.shopapp.domains.sales.api.OrderInternalApi;
import com.project.shopapp.domains.sales.entity.Order;
import com.project.shopapp.domains.sales.enums.PaymentStatus;
import com.project.shopapp.domains.sales.event.OrderPaymentSuccessEvent;
import com.project.shopapp.domains.sales.repository.OrderRepository;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderInternalApiImpl implements OrderInternalApi {

    private final OrderRepository orderRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void markOrderAsPaid(String orderCode) {
        Order order = orderRepo.findByOrderCodeAndIsDeleted(orderCode, 0L)
                .orElseThrow(() -> new DataNotFoundException("Order Code không hợp lệ"));

        if (order.getPaymentStatus() != PaymentStatus.PAID) {
            order.setPaymentStatus(PaymentStatus.PAID);
            orderRepo.save(order);
            // Bắn event để Tách Tiền (Routing) cho các Shop con (orders_shop)
            eventPublisher.publishEvent(new OrderPaymentSuccessEvent(order.getId(), order.getOrderCode()));
        }
    }

    @Override
    @Transactional
    public void markOrderAsPaymentFailed(String orderCode) {
        Order order = orderRepo.findByOrderCodeAndIsDeleted(orderCode, 0L).orElseThrow();
        order.setPaymentStatus(PaymentStatus.FAILED);
        orderRepo.save(order);
    }
}