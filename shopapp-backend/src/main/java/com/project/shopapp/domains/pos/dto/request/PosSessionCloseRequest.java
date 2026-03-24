package com.project.shopapp.domains.pos.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PosSessionCloseRequest {
    @NotNull(message = "Tiền mặt thực tế đếm được không được để trống")
    @Min(value = 0, message = "Tiền mặt không được âm")
    private BigDecimal closingCash;

    private String note; // Bắt buộc nhập nếu tiền bị lệch
}