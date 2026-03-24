package com.project.shopapp.domains.marketing.dto.nested;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CouponBasicDto {
    private Integer id;
    private String code;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal maxDiscountAmount;
    // Dùng nhúng vào CartItem để cho người dùng biết Mã này giảm được bao nhiêu
}