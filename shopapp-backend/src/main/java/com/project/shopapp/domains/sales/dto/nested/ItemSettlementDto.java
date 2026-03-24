// --- nested/ItemSettlementDto.java ---
package com.project.shopapp.domains.sales.dto.nested;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemSettlementDto {
    private Integer id;
    private String productName;
    private BigDecimal costPrice;
    private Integer supplierId;
    // Dùng cho màn hình Kế toán chốt công nợ với Nhà Cung Cấp
}