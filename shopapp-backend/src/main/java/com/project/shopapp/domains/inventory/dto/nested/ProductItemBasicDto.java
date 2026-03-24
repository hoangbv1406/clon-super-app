package com.project.shopapp.domains.inventory.dto.nested;
import lombok.Data;

@Data
public class ProductItemBasicDto {
    private Integer id;
    private String imeiCode;
    private String status;
    // Nhúng vào cấu hình OrderDetail để xuất bill cho khách
}