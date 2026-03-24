// --- response/WalletTransResponse.java ---
package com.project.shopapp.domains.finance.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransResponse {
    private Long id;
    private Integer walletId;
    private BigDecimal amount;
    private String type;
    private String description;
    private Long refOrderId;
    private String referenceCode;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;
}