// --- validation/ValidMessagePayload.java ---
package com.project.shopapp.domains.chat.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MessagePayloadValidator.class)
@Target({ElementType.TYPE}) // Validate cấp độ Class
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMessagePayload {
    String message() default "Payload không hợp lệ với loại tin nhắn này";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}