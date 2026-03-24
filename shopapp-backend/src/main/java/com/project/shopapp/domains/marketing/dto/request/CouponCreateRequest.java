package com.project.shopapp.domains.marketing.dto.request;
import com.project.shopapp.domains.marketing.validation.ValidCouponLogic;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ValidCouponLogic // Custom Validator để check logic Giảm % thì value <= 100
public class CouponCreateRequest {
    @NotBlank(message = "Mã voucher không được trống")
    private String code;

    @NotBlank(message = "Tên voucher không được trống")
    private String name;

    private String description;

    @NotNull(message = "Loại giảm giá không được trống")
    private String discountType;

    @NotNull(message = "Giá trị giảm không được trống")
    @Min(0)
    private BigDecimal discountValue;

    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderAmount;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    @Future(message = "Ngày kết thúc phải ở tương lai")
    private LocalDateTime endDate;

    private Integer usageLimit;
    private Integer usagePerUser;
    private BigDecimal totalBudget;
}