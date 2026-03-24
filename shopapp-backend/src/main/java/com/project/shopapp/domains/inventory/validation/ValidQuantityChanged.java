package com.project.shopapp.domains.inventory.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = QuantityChangedValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuantityChanged {
    String message() default "Số lượng thay đổi không được phép bằng 0";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}