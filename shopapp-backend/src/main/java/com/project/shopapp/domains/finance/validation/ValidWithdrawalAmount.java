// --- validation/ValidWithdrawalAmount.java ---
package com.project.shopapp.domains.finance.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WithdrawalAmountValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWithdrawalAmount {
    String message() default "Số tiền rút tối thiểu là 50,000 VNĐ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}