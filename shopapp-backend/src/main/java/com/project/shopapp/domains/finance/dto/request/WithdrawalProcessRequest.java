// --- request/WithdrawalProcessRequest.java ---
package com.project.shopapp.domains.finance.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WithdrawalProcessRequest {
    @NotNull(message = "Trạng thái duyệt không được để trống (true/false)")
    private Boolean isApproved;

    private String bankTransferRef; // Bắt buộc nếu isApproved = true
    private String adminNote;       // Bắt buộc nếu isApproved = false
}