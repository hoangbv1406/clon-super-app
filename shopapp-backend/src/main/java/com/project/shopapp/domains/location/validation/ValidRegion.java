package com.project.shopapp.domains.location.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegionValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRegion {
    String message() default "Vùng miền không hợp lệ (NORTH, CENTRAL, SOUTH, UNKNOWN)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}