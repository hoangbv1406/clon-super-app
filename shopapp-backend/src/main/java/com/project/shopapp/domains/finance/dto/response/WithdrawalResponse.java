// --- response/WithdrawalResponse.java ---
package com.project.shopapp.domains.finance.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class WithdrawalResponse {
    private Integer id;
    private Integer userId;
    private String userName; // Tên shop/user
    private BigDecimal amount;
    private String bankName;
    private String bankAccount;
    private String accountHolder;
    private String status;
    private String approverName;
    private String bankTransferRef;
    private String adminNote;
    private LocalDateTime resolvedAt;
    private LocalDateTime createdAt;
}