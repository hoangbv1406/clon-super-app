// --- nested/PaymentUrlDto.java ---
package com.project.shopapp.domains.finance.dto.nested;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentUrlDto {
    private String paymentUrl; // Trả về FE để FE redirect khách sang trang Ngân hàng
}