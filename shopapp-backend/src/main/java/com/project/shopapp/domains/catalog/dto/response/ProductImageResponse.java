package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ProductImageResponse extends BaseResponse {
    private Integer id;
    private Integer productId;
    private String imageUrl;
    private Integer displayOrder;
    private String imageType;
}