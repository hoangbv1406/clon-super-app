package com.project.shopapp.domains.pos.dto.nested;
import lombok.Data;

@Data
public class PosSessionBasicDto {
    private Integer id;
    private String status;
    private String cashierName;
}
