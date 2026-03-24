package com.project.shopapp.domains.location.dto.nested;
import lombok.Data;

@Data
public class WardBasicDto {
    private String code;
    private String name;
    private String deliveryStatus; // Rất quan trọng để FE show UI "Không hỗ trợ giao" ngay trong giỏ hàng
}