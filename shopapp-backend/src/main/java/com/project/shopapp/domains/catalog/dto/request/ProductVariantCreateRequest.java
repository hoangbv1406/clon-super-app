package com.project.shopapp.domains.catalog.dto.request;
import com.project.shopapp.domains.catalog.validation.ValidSku;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductVariantCreateRequest {
    @NotNull(message = "ID Sản phẩm gốc không được trống")
    private Integer productId;

    @ValidSku
    private String sku; // Nếu rỗng, hệ thống sẽ tự sinh

    @NotNull(message = "Giá bán không được trống")
    @Min(0)
    private BigDecimal price;

    private BigDecimal originalPrice;
    private String imageUrl;

    @Min(0)
    private Integer quantity;

    private BigDecimal weight;
    private String dimensions;

    // FE chỉ cần gửi lên mảng ID của các OptionValues (VD: [1, 5] -> Đỏ, 256GB)
    // Hệ thống sẽ tự map thành chuỗi JSON attributes
    @NotNull(message = "Danh sách thuộc tính không được để trống")
    private List<Integer> optionValueIds;
}