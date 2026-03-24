// --- request/WarrantyProcessRequest.java ---
package com.project.shopapp.domains.after_sales.dto.request;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WarrantyProcessRequest {
    private String status; // APPROVED, REJECTED, COMPLETED...
    private String adminNote;
    private BigDecimal refundAmount; // Chỉ nhập khi status = COMPLETED và loại là RETURN
}