// --- validation/ValidPosStatus.java ---
package com.project.shopapp.domains.pos.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PosStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPosStatus {
    String message() default "Trạng thái ca làm việc không hợp lệ (OPEN, CLOSED)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
