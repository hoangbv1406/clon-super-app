package com.project.shopapp.domains.identity.dto.response;
import com.project.shopapp.domains.location.dto.nested.DistrictBasicDto;
import com.project.shopapp.domains.location.dto.nested.ProvinceBasicDto;
import com.project.shopapp.domains.location.dto.nested.WardBasicDto;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class UserAddressResponse {
    private Integer id;
    private String recipientName;
    private String phoneNumber;
    private String addressDetail;
    private Boolean isDefault;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String addressType;

    // Nồng ghép các đối tượng vị trí vào để FE hiển thị: "Số 123, Phường X, Quận Y, Tỉnh Z"
    private ProvinceBasicDto province;
    private DistrictBasicDto district;
    private WardBasicDto ward;
}
