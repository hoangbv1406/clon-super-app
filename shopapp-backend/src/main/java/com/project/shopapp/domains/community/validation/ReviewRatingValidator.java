// --- validation/ReviewRatingValidator.java ---
package com.project.shopapp.domains.community.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReviewRatingValidator implements ConstraintValidator<ValidReviewRating, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value >= 1 && value <= 5;
    }
}