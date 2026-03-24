package com.project.shopapp.domains.inventory.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SupplierStatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSupplierStatus {
    String message() default "Trạng thái không hợp lệ (ACTIVE, INACTIVE)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}