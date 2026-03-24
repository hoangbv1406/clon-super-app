package com.project.shopapp.domains.inventory.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionType {
    String message() default "Loại giao dịch không hợp lệ (INBOUND, OUTBOUND, ADJUSTMENT, RETURN)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}