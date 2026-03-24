package com.project.shopapp.domains.finance.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BankTransferIntegrationConfig {
    // TODO: Khai báo Bean kết nối với API Chi hộ của Ngân hàng (Ví dụ: OCB, Techcombank hay Napas).
    // Khi Admin bấm APPROVED trên giao diện, thay vì kế toán phải mở app ngân hàng chuyển khoản bằng tay,
    // Hệ thống sẽ gọi API Chi hộ để Ngân hàng tự động bắn tiền thẳng vào tài khoản của Shop,
    // sau đó lấy mã UNC trả về tự động.
}