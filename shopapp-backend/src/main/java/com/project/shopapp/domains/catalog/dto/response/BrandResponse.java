package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class BrandResponse extends BaseResponse {
    private Integer id;
    private String name;
    private String slug;
    private String iconUrl;
    private String description;
    private String tier;
    private Boolean isActive;
}