package com.project.shopapp.domains.marketing.validation;
import com.project.shopapp.domains.marketing.dto.request.BannerCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BannerDateRangeValidator implements ConstraintValidator<ValidBannerDateRange, BannerCreateRequest> {
    @Override
    public boolean isValid(BannerCreateRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;
        if (request.getStartTime() == null || request.getEndTime() == null) return true;
        return request.getEndTime().isAfter(request.getStartTime());
    }
}