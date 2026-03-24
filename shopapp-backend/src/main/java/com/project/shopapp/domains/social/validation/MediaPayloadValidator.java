// --- validation/MediaPayloadValidator.java ---
package com.project.shopapp.domains.social.validation;
import com.project.shopapp.domains.social.dto.request.PostCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MediaPayloadValidator implements ConstraintValidator<ValidMediaPayload, PostCreateRequest> {
    @Override
    public boolean isValid(PostCreateRequest request, ConstraintValidatorContext context) {
        if (request.getMediaUrls() == null || request.getMediaUrls().isEmpty()) {
            return false; // Social Post BẮT BUỘC phải có hình/video
        }
        if ("VIDEO".equalsIgnoreCase(request.getMediaType())) {
            return request.getMediaUrls().size() == 1;
        } else if ("IMAGE".equalsIgnoreCase(request.getMediaType())) {
            return request.getMediaUrls().size() <= 9;
        }
        return false;
    }
}