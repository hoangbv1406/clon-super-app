// --- response/CouponUsageResponse.java ---
package com.project.shopapp.domains.marketing.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class CouponUsageResponse extends BaseResponse {
    private Long id;
    private Integer userId;
    private String userName; // Tên khách hàng (phục vụ Admin CMS)
    private Integer couponId;
    private String couponCode; // Mã voucher (phục vụ Admin CMS)
    private Long orderId;
    private BigDecimal discountAmount;
    private LocalDateTime usedAt;
    private String status;
}