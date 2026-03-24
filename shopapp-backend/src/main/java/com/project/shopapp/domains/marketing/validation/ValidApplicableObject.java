// --- validation/ValidApplicableObject.java ---
package com.project.shopapp.domains.marketing.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ApplicableObjectValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidApplicableObject {
    String message() default "Đối tượng không hợp lệ (CATEGORY, PRODUCT, BRAND)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}