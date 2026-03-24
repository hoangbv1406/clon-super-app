package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductType {
    String message() default "Loại sản phẩm không hợp lệ (OWN, CONSIGNED)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}