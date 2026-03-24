// --- response/WalletResponse.java ---
package com.project.shopapp.domains.finance.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class WalletResponse {
    private Integer id;
    private Integer userId;
    private BigDecimal balance;       // Tiền có thể rút
    private BigDecimal frozenBalance; // Tiền đang bị giam chờ đối soát
    private String status;
}