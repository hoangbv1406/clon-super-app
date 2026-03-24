// --- validation/ValidMediaPayload.java ---
package com.project.shopapp.domains.social.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MediaPayloadValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMediaPayload {
    String message() default "Payload media không hợp lệ (Video chỉ được 1 file, Ảnh tối đa 9 file)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}