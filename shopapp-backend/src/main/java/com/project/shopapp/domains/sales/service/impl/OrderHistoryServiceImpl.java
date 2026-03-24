// --- service/impl/OrderHistoryServiceImpl.java ---
package com.project.shopapp.domains.sales.service.impl;

import com.project.shopapp.domains.sales.dto.response.OrderHistoryResponse;
import com.project.shopapp.domains.sales.mapper.OrderHistoryMapper;
import com.project.shopapp.domains.sales.repository.OrderHistoryRepository;
import com.project.shopapp.domains.sales.service.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository historyRepo;
    private final OrderHistoryMapper historyMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> getOrderTimeline(Long orderId) {
        return historyRepo.findByOrderIdAndOrderShopIdIsNullOrderByCreatedAtDesc(orderId)
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderHistoryResponse> getOrderShopTimeline(Integer orderShopId) {
        return historyRepo.findByOrderShopIdOrderByCreatedAtDesc(orderShopId)
                .stream().map(historyMapper::toDto).collect(Collectors.toList());
    }
}