// --- response/VariantMatrixResponse.java ---
package com.project.shopapp.domains.catalog.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariantMatrixResponse {
    private Integer variantId;
    private String optionName;
    private String valueName;
    private String metaData; // Hex color
}