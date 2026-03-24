package com.project.shopapp.domains.catalog.validation;
import com.project.shopapp.domains.catalog.dto.request.OptionValueCreateRequest;
import com.project.shopapp.domains.catalog.entity.Option;
import com.project.shopapp.domains.catalog.enums.OptionType;
import com.project.shopapp.domains.catalog.repository.OptionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class MetaDataValidator implements ConstraintValidator<ValidMetaData, OptionValueCreateRequest> {

    private final OptionRepository optionRepo;
    private static final Pattern HEX_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    @Override
    public boolean isValid(OptionValueCreateRequest request, ConstraintValidatorContext context) {
        if (request.getOptionId() == null) return true; // Sẽ bị bắt bởi @NotNull

        Option option = optionRepo.findById(request.getOptionId()).orElse(null);
        if (option == null) return true; // Sẽ bị bắt ở Service

        if (option.getType() == OptionType.COLOR_HEX) {
            if (request.getMetaData() == null || !HEX_PATTERN.matcher(request.getMetaData()).matches()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Option này yêu cầu MetaData phải là mã màu HEX (VD: #FF0000)")
                        .addPropertyNode("metaData").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}