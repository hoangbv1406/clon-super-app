package com.project.shopapp.domains.inventory.dto.response;
import com.project.shopapp.shared.base.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class SupplierResponse extends BaseResponse {
    private Integer id;
    private Integer shopId;
    private String name;
    private String contactEmail;
    private String contactPhone;
    private String taxCode;
    private String address;
    private BigDecimal totalDebt;
    private String status;
}