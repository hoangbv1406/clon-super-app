package com.project.shopapp.domains.vendor.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class ShopEmployeeResponse extends BaseResponse {
    private Integer id;
    private Integer shopId;
    private Integer userId;
    private String userName;  // Tên NV (Lấy từ User Entity)
    private String userEmail; // Email NV
    private String role;
    private String status;
}