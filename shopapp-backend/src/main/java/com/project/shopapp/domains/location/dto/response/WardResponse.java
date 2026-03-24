package com.project.shopapp.domains.location.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class WardResponse extends BaseResponse {
    private String code;
    private String districtCode;
    private String name;
    private String fullName;
    private String deliveryStatus;
    private Boolean isActive;
}