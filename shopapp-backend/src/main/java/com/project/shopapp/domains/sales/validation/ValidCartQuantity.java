// --- validation/ValidCartQuantity.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CartQuantityValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCartQuantity {
    String message() default "Số lượng phải từ 1 đến 999";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}