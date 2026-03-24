package com.project.shopapp.domains.finance.enums;

public enum WalletTransType {
    DEPOSIT,         // Nạp tiền vào ví
    WITHDRAWAL,      // Rút tiền khỏi ví
    EARNING,         // Doanh thu bán hàng (Tiền từ khách vào thẳng balance)
    HOLD,            // Đóng băng tiền (Chờ đối soát sau khi giao hàng)
    UNFREEZE,        // Rã đông tiền (Từ frozen_balance sang balance)
    REFUND,          // Trả lại tiền cho khách / Shop bị trừ tiền do khách hoàn hàng
    COMMISSION_FEE,  // Hệ thống thu phí sàn
    COMPENSATION     // Giao dịch bù trừ sửa sai (Kế toán can thiệp)
}