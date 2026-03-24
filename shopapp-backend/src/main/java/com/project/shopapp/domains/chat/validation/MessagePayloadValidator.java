// --- validation/MessagePayloadValidator.java ---
package com.project.shopapp.domains.chat.validation;
import com.project.shopapp.domains.chat.dto.request.MessageSendRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MessagePayloadValidator implements ConstraintValidator<ValidMessagePayload, MessageSendRequest> {
    @Override
    public boolean isValid(MessageSendRequest request, ConstraintValidatorContext context) {
        if (request.getType() == null) return false;

        switch (request.getType().toUpperCase()) {
            case "TEXT":
                return request.getContent() != null && !request.getContent().trim().isEmpty();
            case "IMAGE":
            case "VIDEO":
            case "PRODUCT":
            case "ORDER":
                return request.getAttachmentUrl() != null && !request.getAttachmentUrl().isEmpty();
            default:
                return false;
        }
    }
}