// --- validation/WithdrawalAmountValidator.java ---
package com.project.shopapp.domains.finance.validation;
import com.project.shopapp.domains.finance.constant.FinanceConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class WithdrawalAmountValidator implements ConstraintValidator<ValidWithdrawalAmount, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.compareTo(FinanceConstants.MIN_WITHDRAWAL_AMOUNT) >= 0;
    }
}