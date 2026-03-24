package com.project.shopapp.domains.identity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeviceTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeviceType {
    String message() default "Hệ điều hành thiết bị không hợp lệ (ANDROID, IOS, WEB)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
// Note: Tạo class DeviceTypeValidator check String có khớp với Enum không.