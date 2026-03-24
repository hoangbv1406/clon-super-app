// --- response/OrderHistoryResponse.java ---
package com.project.shopapp.domains.sales.dto.response;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderHistoryResponse {
    private Integer id;
    private Long orderId;
    private Integer orderShopId;
    private String status;
    private String note;
    private String updaterName; // Tên người đổi trạng thái (hoặc "Hệ thống")
    private LocalDateTime createdAt;
}