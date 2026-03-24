package com.project.shopapp.domains.catalog.validation;
import com.project.shopapp.domains.catalog.enums.ProductImageType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ProductImageTypeValidator implements ConstraintValidator<ValidProductImageType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return Arrays.stream(ProductImageType.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}