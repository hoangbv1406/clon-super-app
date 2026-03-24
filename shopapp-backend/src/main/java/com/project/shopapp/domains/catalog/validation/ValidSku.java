package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SkuValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSku {
    String message() default "Mã SKU chỉ được chứa chữ cái, số và dấu gạch ngang";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}