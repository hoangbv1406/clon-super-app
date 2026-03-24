// --- validation/ValidCarrier.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CarrierValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCarrier {
    String message() default "Đơn vị vận chuyển không được hỗ trợ (Hỗ trợ: GHTK, GHN, VIETTEL_POST, J&T)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}