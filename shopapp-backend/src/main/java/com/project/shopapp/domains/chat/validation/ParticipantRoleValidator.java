// --- validation/ParticipantRoleValidator.java ---
package com.project.shopapp.domains.chat.validation;
import com.project.shopapp.domains.chat.enums.ParticipantRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ParticipantRoleValidator implements ConstraintValidator<ValidParticipantRole, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return Arrays.stream(ParticipantRole.values()).anyMatch(e -> e.name().equals(value.toUpperCase().trim()));
    }
}