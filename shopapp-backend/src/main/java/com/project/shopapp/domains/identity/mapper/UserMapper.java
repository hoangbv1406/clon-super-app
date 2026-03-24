package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.nested.UserBasicDto;
import com.project.shopapp.domains.identity.dto.request.UserRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.UserResponse;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserResponse, User> {

    // Bổ sung vào domains/identity/mapper/UserMapper.java
    UserBasicDto toBasicDto(User entity);

    @Mapping(target = "password", ignore = true)
        // Pass phải được encode ở Service, không map trực tiếp
    User toEntityFromRegisterRequest(UserRegisterRequest request);
}