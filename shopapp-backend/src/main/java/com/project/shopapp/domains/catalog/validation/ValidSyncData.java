package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SyncDataValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSyncData {
    String message() default "Dữ liệu đồng bộ Variant Value bị thiếu ID cốt lõi";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}