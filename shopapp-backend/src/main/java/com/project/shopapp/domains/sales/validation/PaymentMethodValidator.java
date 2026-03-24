// --- validation/PaymentMethodValidator.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class PaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, String> {
    private static final List<String> ACCEPTED_METHODS = List.of("COD", "VNPAY", "MOMO");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return ACCEPTED_METHODS.contains(value.toUpperCase().trim());
    }
}