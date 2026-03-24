// --- response/CategoryResponse.java ---
package com.project.shopapp.domains.catalog.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class CategoryResponse extends BaseResponse {
    private Integer id;
    private String name;
    private Integer parentId;
    private String path;
    private Integer level;
    private String slug;
    private String iconUrl;
    private Integer displayOrder;
    private Boolean isActive;
    private String displayMode;
    // Đệ quy lấy danh sách con (Sẽ được map bằng logic ở Service để tránh N+1)
    private List<CategoryResponse> children;
}