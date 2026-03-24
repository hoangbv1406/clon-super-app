package com.project.shopapp.domains.review.validation;
import com.project.shopapp.domains.review.enums.ReviewStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ReviewStatusValidator implements ConstraintValidator<ValidReviewStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(ReviewStatus.values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}