package com.project.shopapp.domains.vendor.mapper;

import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeCreateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopEmployeeResponse;
import com.project.shopapp.domains.vendor.entity.ShopEmployee;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopEmployeeMapper extends BaseMapper<ShopEmployeeResponse, ShopEmployee> {

    // Gắn sẵn Mapping lấy tên và email từ bảng User Lazy
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.email", target = "userEmail")
    ShopEmployeeResponse toDto(ShopEmployee entity);

    ShopEmployee toEntityFromCreateRequest(ShopEmployeeCreateRequest request);
}