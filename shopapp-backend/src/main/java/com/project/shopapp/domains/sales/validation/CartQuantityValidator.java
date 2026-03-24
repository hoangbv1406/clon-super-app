// --- validation/CartQuantityValidator.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CartQuantityValidator implements ConstraintValidator<ValidCartQuantity, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value > 0 && value <= 999;
    }
}