package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.request.DeviceRegistrationRequest;
import com.project.shopapp.domains.identity.dto.response.UserDeviceResponse;
import com.project.shopapp.domains.identity.entity.UserDevice;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDeviceMapper extends BaseMapper<UserDeviceResponse, UserDevice> {
    UserDevice toEntityFromRequest(DeviceRegistrationRequest request);
}