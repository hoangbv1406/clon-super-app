// --- request/OrderShopFulfillmentRequest.java ---
package com.project.shopapp.domains.sales.dto.request;
import com.project.shopapp.domains.sales.validation.ValidCarrier;
import lombok.Data;

@Data
public class OrderShopFulfillmentRequest {
    @ValidCarrier
    private String carrierName; // Đơn vị vận chuyển
    private String trackingNumber; // Mã vận đơn
    private String shopNote;
}