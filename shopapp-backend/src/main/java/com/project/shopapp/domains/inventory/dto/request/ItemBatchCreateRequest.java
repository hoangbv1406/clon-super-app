package com.project.shopapp.domains.inventory.dto.request;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemBatchCreateRequest {
    @NotNull(message = "Product ID không được trống")
    private Integer productId;

    private Integer variantId; // Có thể null nếu SP không có biến thể
    private Integer supplierId;

    @NotNull(message = "Giá nhập không được trống")
    private BigDecimal inboundPrice;

    @NotEmpty(message = "Danh sách IMEI không được rỗng")
    private List<String> imeiCodes; // Nhập kho 1 lúc bắn barcode 100 cái IMEI
}