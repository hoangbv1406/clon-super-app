// --- response/TransactionResponse.java ---
package com.project.shopapp.domains.finance.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Integer id;
    private Long orderId;
    private String paymentMethod;
    private String transactionCode;
    private BigDecimal amount;
    private String type;
    private String status;
    private String gatewayCode;
    private String errorMessage;
    private Integer parentTransactionId;
    private LocalDateTime createdAt;
}