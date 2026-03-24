package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductImageTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductImageType {
    String message() default "Loại hình ảnh không hợp lệ (GALLERY, SIZE_GUIDE, CERTIFICATE)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}