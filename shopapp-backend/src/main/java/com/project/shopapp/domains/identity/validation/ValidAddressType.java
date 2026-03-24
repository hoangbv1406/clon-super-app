package com.project.shopapp.domains.identity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AddressTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddressType {
    String message() default "Loại địa chỉ chỉ chấp nhận HOME hoặc OFFICE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
// Note: AddressTypeValidator implement check String khớp với Enum AddressType.