// --- request/WarrantyCreateRequest.java ---
package com.project.shopapp.domains.after_sales.dto.request;
import com.project.shopapp.domains.after_sales.validation.ValidRequestType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class WarrantyCreateRequest {
    @NotNull(message = "ID Chi tiết đơn hàng không được trống")
    private Integer orderDetailId;

    private Integer productItemId; // Nếu là hàng bán theo IMEI

    @ValidRequestType
    private String requestType;

    @NotBlank(message = "Vui lòng nhập lý do khiếu nại")
    private String reason;

    private List<String> images; // Link ảnh upload lên S3/Cloudinary

    @Min(value = 1, message = "Số lượng ít nhất là 1")
    private Integer quantity;
}