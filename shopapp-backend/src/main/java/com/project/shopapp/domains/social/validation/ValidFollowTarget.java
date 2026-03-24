// --- validation/ValidFollowTarget.java ---
package com.project.shopapp.domains.social.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FollowTargetValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFollowTarget {
    String message() default "Dữ liệu Follow không hợp lệ. Phải gửi đúng ID khớp với followType.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}