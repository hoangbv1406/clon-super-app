package com.project.shopapp.domains.location.dto.nested;
import lombok.Data;

@Data
public class DistrictBasicDto {
    private String code;
    private String name;
    private String type; // Trả về type để FE biết mà disable nút "Giao Hỏa Tốc" nếu là ISLAND
}