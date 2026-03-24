package com.project.shopapp.domains.finance.constant;

import java.math.BigDecimal;

public final class FinanceConstants {
    private FinanceConstants() {}
    public static final String VNPAY_SUCCESS_CODE = "00";
    public static final BigDecimal MIN_WITHDRAWAL_AMOUNT = new BigDecimal("50000.00"); // Rút tối thiểu 50k
}