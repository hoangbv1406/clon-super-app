package com.project.shopapp.domains.location.dto.request;
import com.project.shopapp.domains.location.validation.ValidDeliveryStatus;
import lombok.Data;

@Data
public class WardStatusUpdateRequest {
    @ValidDeliveryStatus
    private String deliveryStatus;
    private String reason; // Ghi chú lý do ngưng giao (VD: "Lũ lụt miền Trung")
}