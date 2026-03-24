package com.project.shopapp.domains.inventory.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private Integer shopId;
    private String transactionCode;
    private String type;
    private String referenceType;
    private Long referenceId;
    private String note;
    private String status;
    private BigDecimal totalValue;
    private String creatorName; // Lấy từ bảng User
    private LocalDateTime createdAt;
}