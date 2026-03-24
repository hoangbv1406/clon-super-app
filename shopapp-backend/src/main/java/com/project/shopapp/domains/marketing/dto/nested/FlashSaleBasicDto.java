// --- nested/FlashSaleBasicDto.java ---
package com.project.shopapp.domains.marketing.dto.nested;
import lombok.Data;

@Data
public class FlashSaleBasicDto {
    private Integer id;
    private String name;
    private String status;
    // Dùng nhúng vào entity FlashSaleItem ở bài sau
}