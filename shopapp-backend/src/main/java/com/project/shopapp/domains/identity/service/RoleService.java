package com.project.shopapp.domains.identity.service;
import com.project.shopapp.domains.identity.dto.request.RoleRequest;
import com.project.shopapp.domains.identity.dto.response.RoleResponse;
import java.util.List;

public interface RoleService {
    List<RoleResponse> getAllRoles();
    RoleResponse createRole(RoleRequest request);
    RoleResponse updateRoleStatus(Integer id, boolean isActive);
}