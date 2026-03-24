// --- validation/ValidReviewRating.java ---
package com.project.shopapp.domains.community.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReviewRatingValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReviewRating {
    String message() default "Số sao đánh giá phải từ 1 đến 5";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}