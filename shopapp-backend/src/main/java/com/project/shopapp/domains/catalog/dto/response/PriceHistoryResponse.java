package com.project.shopapp.domains.catalog.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PriceHistoryResponse {
    private Integer id;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private String priceType;
    private String reason;
    private String updaterName; // Tên Admin/Vendor đã đổi giá
    private LocalDateTime createdAt;
}