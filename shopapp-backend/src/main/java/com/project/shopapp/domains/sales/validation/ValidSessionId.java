// --- validation/ValidSessionId.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SessionIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSessionId {
    String message() default "Session ID không được để trống và phải có ít nhất 16 ký tự";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}