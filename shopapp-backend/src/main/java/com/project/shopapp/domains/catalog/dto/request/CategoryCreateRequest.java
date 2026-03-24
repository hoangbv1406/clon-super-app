package com.project.shopapp.domains.catalog.dto.request;
import com.project.shopapp.domains.catalog.validation.ValidCategoryDisplayMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateRequest {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    private Integer parentId;
    private String iconUrl;
    private Integer displayOrder;
    @ValidCategoryDisplayMode
    private String displayMode;
}