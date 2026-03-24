package com.project.shopapp.domains.identity.dto.request;
import com.project.shopapp.domains.identity.validation.ValidAddressType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserAddressRequest {
    @NotBlank(message = "Tên người nhận không được để trống")
    private String recipientName;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    private String addressDetail;

    @NotBlank(message = "Mã tỉnh thành không được để trống")
    private String provinceCode;

    @NotBlank(message = "Mã quận huyện không được để trống")
    private String districtCode;

    @NotBlank(message = "Mã phường xã không được để trống")
    private String wardCode;

    private Boolean isDefault;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @ValidAddressType
    private String addressType;
}