// --- validation/ValidParticipantRole.java ---
package com.project.shopapp.domains.chat.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ParticipantRoleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidParticipantRole {
    String message() default "Role không hợp lệ (Chỉ chấp nhận ADMIN, MEMBER)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}