package com.project.shopapp.domains.inventory.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TaxCodeValidator implements ConstraintValidator<ValidTaxCode, String> {
    private static final Pattern TAX_PATTERN = Pattern.compile("^\\d{10}(-\\d{3})?$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true;
        return TAX_PATTERN.matcher(value).matches();
    }
}