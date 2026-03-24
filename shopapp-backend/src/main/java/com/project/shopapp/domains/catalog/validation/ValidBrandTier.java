// --- validation/ValidBrandTier.java ---
package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BrandTierValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBrandTier {
    String message() default "Phân hạng thương hiệu không hợp lệ (PREMIUM, REGULAR, LOCAL)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
