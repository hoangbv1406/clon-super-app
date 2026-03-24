package com.project.shopapp.domains.marketing.dto.response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicableResponse {
    private Integer id;
    private Integer couponId;
    private String objectType;
    private Integer objectId;
    private String objectName; // Sẽ được Service gọi sang Catalog để lấy tên (VD: "Điện thoại iPhone")
    private String applicableType;
}