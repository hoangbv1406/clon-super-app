// --- validation/ValidNotificationType.java ---
package com.project.shopapp.domains.notification.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotificationTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNotificationType {
    String message() default "Loại thông báo không hợp lệ (ORDER, SYSTEM, PROMOTION, CHAT)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}