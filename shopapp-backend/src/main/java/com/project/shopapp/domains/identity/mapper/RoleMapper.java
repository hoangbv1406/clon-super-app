package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.request.RoleRequest;
import com.project.shopapp.domains.identity.dto.response.RoleResponse;
import com.project.shopapp.domains.identity.entity.Role;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper<RoleResponse, Role> {
    Role toEntityFromRequest(RoleRequest request);
}