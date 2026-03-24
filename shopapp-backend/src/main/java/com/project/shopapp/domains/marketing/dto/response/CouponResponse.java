package com.project.shopapp.domains.marketing.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class CouponResponse extends BaseResponse {
    private Integer id;
    private Integer shopId;
    private String code;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimit;
    private Integer usedCount;
    private Integer usagePerUser;
    private BigDecimal totalBudget;
    private BigDecimal usedBudget;
    private Boolean isActive;
    private Boolean isExpired; // Custom field từ Entity
}