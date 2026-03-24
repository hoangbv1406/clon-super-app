// --- validation/ValidPaymentMethod.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaymentMethodValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPaymentMethod {
    String message() default "Phương thức thanh toán không hợp lệ (Chỉ chấp nhận COD, VNPAY, MOMO)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}