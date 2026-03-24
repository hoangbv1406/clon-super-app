package com.project.shopapp.domains.location.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class DistrictResponse extends BaseResponse {
    private String code;
    private String provinceCode; // Chỉ trả về mã tỉnh cho nhẹ, FE tự map với list Tỉnh đã có
    private String name;
    private String fullName;
    private String type;
    private Boolean isActive;
}