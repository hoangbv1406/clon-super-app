// --- response/WarrantyResponse.java ---
package com.project.shopapp.domains.after_sales.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class WarrantyResponse extends BaseResponse {
    private Integer id;
    private String requestCode;
    private Integer userId;
    private Integer shopId;
    private Integer orderDetailId;
    private String requestType;
    private String status;
    private String reason;
    private List<String> images;
    private String adminNote;
    private Integer quantity;
    private String returnTrackingCode;
    private BigDecimal refundAmount;
}