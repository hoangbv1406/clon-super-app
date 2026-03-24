// --- response/OrderDetailResponse.java ---
package com.project.shopapp.domains.sales.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
public class OrderDetailResponse {
    private Integer id;
    private Long orderId;
    private Integer orderShopId;

    // UI sẽ đọc từ các trường Snapshot này, kể cả khi Product bị xóa thì đơn hàng vẫn hiển thị bình thường
    private String productName;
    private String variantName;
    private String productThumbnailSnapshot;

    private Integer productId;
    private Integer variantId;
    private Integer productItemId; // IMEI/Serial

    private BigDecimal price;
    private Integer numberOfProducts;
    private BigDecimal totalMoney;
    private BigDecimal discountAmount;

    private Map<String, Object> configuration;
    private String itemStatus;
    private LocalDate warrantyExpireDate;
}