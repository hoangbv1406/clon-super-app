package com.project.shopapp.domains.marketing.validation;
import com.project.shopapp.domains.marketing.enums.ApplicableObjectType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ApplicableObjectValidator implements ConstraintValidator<ValidApplicableObject, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(ApplicableObjectType.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}