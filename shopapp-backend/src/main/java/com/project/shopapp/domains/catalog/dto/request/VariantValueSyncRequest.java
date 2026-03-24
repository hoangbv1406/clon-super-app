package com.project.shopapp.domains.catalog.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Dùng cho nội bộ Backend (Message Queue / Event Listener)
public class VariantValueSyncRequest {
    private Integer variantId;
    private Integer productId;
    private Integer optionId;
    private Integer optionValueId;
}