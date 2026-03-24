package com.project.shopapp.domains.identity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeviceLabelValidator.class) // Dùng regex ^[a-zA-Z0-9\s_.-]{3,50}$
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeviceLabel {
    String message() default "Tên thiết bị chứa ký tự không hợp lệ hoặc quá dài";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}