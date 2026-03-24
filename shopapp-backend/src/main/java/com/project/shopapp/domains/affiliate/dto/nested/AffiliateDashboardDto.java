// --- nested/AffiliateDashboardDto.java ---
package com.project.shopapp.domains.affiliate.dto.nested;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AffiliateDashboardDto {
    private BigDecimal totalPending;  // Tiền chờ duyệt
    private BigDecimal totalApproved; // Tiền chuẩn bị được nhận
    private BigDecimal totalPaid;     // Tiền đã rút thành công
}