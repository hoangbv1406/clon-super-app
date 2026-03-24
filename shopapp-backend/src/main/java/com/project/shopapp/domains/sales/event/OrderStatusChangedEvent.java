package com.project.shopapp.domains.sales.event;

import com.project.shopapp.shared.events.DomainEvent;
import lombok.Getter;

@Getter
public class OrderStatusChangedEvent extends DomainEvent {
    private final Long orderId;
    private final Integer orderShopId; // Có thể null nếu chỉ đổi trạng thái đơn tổng
    private final String newStatus;
    private final String note;
    private final Integer updatedBy;

    public OrderStatusChangedEvent(Long orderId, Integer orderShopId, String newStatus, String note, Integer updatedBy) {
        super();
        this.orderId = orderId;
        this.orderShopId = orderShopId;
        this.newStatus = newStatus;
        this.note = note;
        this.updatedBy = updatedBy;
    }
}