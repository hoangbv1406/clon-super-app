// --- validation/CarrierValidator.java ---
package com.project.shopapp.domains.sales.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CarrierValidator implements ConstraintValidator<ValidCarrier, String> {
    private static final List<String> APPROVED_CARRIERS = List.of("GHTK", "GHN", "VIETTEL_POST", "J&T");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Cho phép null khi mới tạo đơn
        return APPROVED_CARRIERS.contains(value.toUpperCase().trim());
    }
}