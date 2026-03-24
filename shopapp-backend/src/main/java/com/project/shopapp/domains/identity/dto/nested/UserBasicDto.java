package com.project.shopapp.domains.identity.dto.nested;

import lombok.Data;

@Data
public class UserBasicDto {
    private Integer id;
    private String fullName;
    private String profileImage; // Chỉ trả về ảnh đại diện và tên để hiển thị ở mục Review/Comment

    // Tuyệt đối không chứa Email, PhoneNumber, Password, Role hay IsActive ở đây!
}