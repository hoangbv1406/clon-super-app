package com.project.shopapp.domains.inventory.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ProductItemResponse extends BaseResponse {
    private Integer id;
    private String imeiCode;
    private Integer productId;
    private Integer variantId;
    private Integer supplierId;
    private Long orderId;
    private BigDecimal inboundPrice;
    private String status;
    private LocalDateTime importDate;
    private LocalDateTime soldDate;
}