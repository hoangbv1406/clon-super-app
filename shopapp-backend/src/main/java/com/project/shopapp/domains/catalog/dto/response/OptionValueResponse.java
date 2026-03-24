package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class OptionValueResponse extends BaseResponse {
    private Integer id;
    private Integer optionId;
    private String value;
    private String metaData;
    private Integer displayOrder;
    private Boolean isActive;
}