package com.project.shopapp.domains.catalog.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MetaDataValidator.class)
@Target({ElementType.TYPE}) // Đặt ở mức Class vì cần truy cập cả optionId và metaData
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMetaData {
    String message() default "Dữ liệu MetaData không hợp lệ so với Option Type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}