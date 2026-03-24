// --- response/AffiliateTransResponse.java ---
package com.project.shopapp.domains.affiliate.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AffiliateTransResponse {
    private Long id;
    private Integer affiliateLinkId;
    private String affiliateCode;     // Sinh ra từ JOIN bảng Link
    private String orderShopCode;     // Sinh ra từ JOIN bảng OrderShop (Mã kiện hàng)
    private BigDecimal amount;
    private String status;
    private LocalDateTime payoutDate;
    private LocalDateTime createdAt;
}