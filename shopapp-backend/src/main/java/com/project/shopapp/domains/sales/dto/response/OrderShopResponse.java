// --- response/OrderShopResponse.java ---
package com.project.shopapp.domains.sales.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class OrderShopResponse extends BaseResponse {
    private Integer id;
    private String orderShopCode;
    private Long parentOrderId;
    private Integer shopId;
    private String shippingMethod;
    private BigDecimal shippingFee;
    private BigDecimal subTotal;
    private BigDecimal adminCommission;
    private BigDecimal shopIncome;
    private String status;
    private String trackingNumber;
    private String carrierName;
    private LocalDateTime shippingDate;
    private String shopNote;
}