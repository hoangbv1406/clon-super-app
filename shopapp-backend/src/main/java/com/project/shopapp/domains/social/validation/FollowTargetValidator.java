// --- validation/FollowTargetValidator.java ---
package com.project.shopapp.domains.social.validation;
import com.project.shopapp.domains.social.dto.request.FollowRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FollowTargetValidator implements ConstraintValidator<ValidFollowTarget, FollowRequest> {
    @Override
    public boolean isValid(FollowRequest request, ConstraintValidatorContext context) {
        if (request.getFollowType() == null) return false;

        if ("USER".equalsIgnoreCase(request.getFollowType())) {
            return request.getTargetUserId() != null && request.getTargetShopId() == null;
        } else if ("SHOP".equalsIgnoreCase(request.getFollowType())) {
            return request.getTargetShopId() != null && request.getTargetUserId() == null;
        }
        return false;
    }
}