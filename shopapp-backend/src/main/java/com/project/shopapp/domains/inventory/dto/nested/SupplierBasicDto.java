package com.project.shopapp.domains.inventory.dto.nested;
import lombok.Data;

@Data
public class SupplierBasicDto {
    private Integer id;
    private String name;
    private String contactPhone;
    // Dùng để nhúng gọn nhẹ vào DTO của Phiếu Nhập Kho (Inventory Transaction)
}