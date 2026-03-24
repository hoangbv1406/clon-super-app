// --- nested/OrderShopBasicDto.java ---
package com.project.shopapp.domains.sales.dto.nested;
import lombok.Data;

@Data
public class OrderShopBasicDto {
    private Integer id;
    private String orderShopCode;
    private String status;
    private String trackingNumber;
    // Dùng nhúng vào bảng OrderResponse để user thấy 1 đơn lớn có mấy kiện hàng con
}