package com.project.shopapp.domains.marketing.dto.request;
import com.project.shopapp.domains.marketing.validation.ValidApplicableObject;
import com.project.shopapp.domains.marketing.validation.ValidApplicableRule;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicableCreateRequest {
    @ValidApplicableObject
    private String objectType;

    @NotNull(message = "ID Đối tượng không được để trống")
    private Integer objectId;

    @ValidApplicableRule
    private String applicableType;
}