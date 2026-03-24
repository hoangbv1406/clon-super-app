// --- nested/CartBasicDto.java ---
package com.project.shopapp.domains.sales.dto.nested;
import lombok.Data;

@Data
public class CartBasicDto {
    private Integer id;
    private String status;
    // Dùng nhúng vào entity Order (nếu cần trace Order này được tạo từ Cart nào)
}