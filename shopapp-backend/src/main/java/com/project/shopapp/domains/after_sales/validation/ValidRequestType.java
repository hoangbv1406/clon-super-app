// --- validation/ValidRequestType.java ---
package com.project.shopapp.domains.after_sales.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequestTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRequestType {
    String message() default "Loại yêu cầu không hợp lệ (WARRANTY, RETURN, EXCHANGE)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}