// --- nested/OptionBasicDto.java ---
package com.project.shopapp.domains.catalog.dto.nested;
import lombok.Data;

@Data
public class OptionBasicDto {
    private Integer id;
    private String code;
    private String name;
    // Dùng nhúng vào mảng cấu hình biến thể của Product
}