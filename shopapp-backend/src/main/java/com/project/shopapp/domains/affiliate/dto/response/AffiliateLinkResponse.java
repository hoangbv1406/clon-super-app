// --- response/AffiliateLinkResponse.java ---
package com.project.shopapp.domains.affiliate.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AffiliateLinkResponse extends BaseResponse {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String productName; // Cần map từ Product
    private String code;
    private String shortUrl;    // Link rút gọn trả về cho KOC (VD: shopapp.vn/go/ABCXYZ)
    private Integer clicks;
    private Integer ordersCount;
    private BigDecimal totalEarnings;
    private Boolean isActive;
    private BigDecimal commissionRate;
}