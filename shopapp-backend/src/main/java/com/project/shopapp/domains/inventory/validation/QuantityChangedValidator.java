package com.project.shopapp.domains.inventory.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityChangedValidator implements ConstraintValidator<ValidQuantityChanged, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value != 0; // Phải khác 0 (âm hoặc dương đều được)
    }
}