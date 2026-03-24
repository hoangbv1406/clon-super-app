// --- validation/NotificationTypeValidator.java ---
package com.project.shopapp.domains.notification.validation;
import com.project.shopapp.domains.notification.enums.NotificationType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class NotificationTypeValidator implements ConstraintValidator<ValidNotificationType, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;
        return Arrays.stream(NotificationType.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}