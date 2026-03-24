package com.project.shopapp.domains.catalog.validation;
import com.project.shopapp.domains.catalog.dto.request.VariantValueSyncRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SyncDataValidator implements ConstraintValidator<ValidSyncData, VariantValueSyncRequest> {
    @Override
    public boolean isValid(VariantValueSyncRequest value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.getVariantId() != null && value.getProductId() != null
                && value.getOptionId() != null && value.getOptionValueId() != null;
    }
}