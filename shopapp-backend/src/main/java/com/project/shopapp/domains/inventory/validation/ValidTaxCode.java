package com.project.shopapp.domains.inventory.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaxCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaxCode {
    String message() default "Mã số thuế phải gồm 10 hoặc 13 chữ số";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}