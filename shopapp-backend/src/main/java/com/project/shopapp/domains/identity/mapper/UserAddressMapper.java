package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.request.UserAddressRequest;
import com.project.shopapp.domains.identity.dto.response.UserAddressResponse;
import com.project.shopapp.domains.identity.entity.UserAddress;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserAddressMapper extends BaseMapper<UserAddressResponse, UserAddress> {
    UserAddress toEntityFromRequest(UserAddressRequest request);
    // THÊM ĐOẠN NÀY ĐỂ UPDATE TỪ REQUEST
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UserAddressRequest request, @MappingTarget UserAddress entity);
}