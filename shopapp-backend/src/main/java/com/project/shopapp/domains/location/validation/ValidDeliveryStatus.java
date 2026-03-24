package com.project.shopapp.domains.location.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DeliveryStatusValidator.class) // Tớ nợ file Validator thực tế nhé, tương tự District
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeliveryStatus {
    String message() default "Trạng thái giao hàng không hợp lệ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}