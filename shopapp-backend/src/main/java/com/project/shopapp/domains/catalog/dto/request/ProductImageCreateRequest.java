// --- request/ProductImageCreateRequest.java ---
package com.project.shopapp.domains.catalog.dto.request;
import com.project.shopapp.domains.catalog.validation.ValidProductImageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductImageCreateRequest {
    @NotNull(message = "ID sản phẩm không được trống")
    private Integer productId;

    @NotBlank(message = "URL hình ảnh không được trống")
    private String imageUrl; // URL sau khi đã được Service xử lý upload lên S3

    private Integer displayOrder;

    @ValidProductImageType
    private String imageType;
}
