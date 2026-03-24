package com.project.shopapp.domains.identity.dto.nested;
import lombok.Data;

@Data
public class UserAddressBasicDto {
    private Integer id;
    private String recipientName;
    private String fullAddressString; // VD: "Ngõ 123, Phường A, Quận B..." (Chuẩn bị sẵn cho Order hiển thị nhanh)
}