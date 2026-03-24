// --- request/WithdrawalCreateRequest.java ---
package com.project.shopapp.domains.finance.dto.request;
import com.project.shopapp.domains.finance.validation.ValidWithdrawalAmount;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WithdrawalCreateRequest {
    @ValidWithdrawalAmount
    private BigDecimal amount;

    @NotBlank(message = "Tên ngân hàng không được để trống")
    private String bankName;

    @NotBlank(message = "Số tài khoản không được để trống")
    private String bankAccount;

    @NotBlank(message = "Tên chủ tài khoản không được để trống")
    private String accountHolder;
}