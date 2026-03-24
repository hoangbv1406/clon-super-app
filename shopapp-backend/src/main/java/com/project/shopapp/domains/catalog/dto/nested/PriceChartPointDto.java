package com.project.shopapp.domains.catalog.dto.nested;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PriceChartPointDto {
    private LocalDate date;
    private BigDecimal price;
    // Dùng riêng để vẽ biểu đồ Line Chart ở Frontend (BeeCost style)
}