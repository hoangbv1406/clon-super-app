package com.project.shopapp.domains.pos.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PosSessionOpenRequest {
    @NotNull(message = "Tiền mặt đầu ca không được để trống")
    @Min(value = 0, message = "Tiền mặt đầu ca không được âm")
    private BigDecimal openingCash;

    private String note;
}