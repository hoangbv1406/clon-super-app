package com.project.shopapp.domains.vendor.service;
import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeCreateRequest;
import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeUpdateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopEmployeeResponse;
import java.util.List;

public interface ShopEmployeeService {
    List<ShopEmployeeResponse> getEmployees(Integer currentUserId, Integer shopId);
    ShopEmployeeResponse addEmployee(Integer currentUserId, Integer shopId, ShopEmployeeCreateRequest request);
    ShopEmployeeResponse updateEmployee(Integer currentUserId, Integer shopId, Integer employeeId, ShopEmployeeUpdateRequest request);
    void removeEmployee(Integer currentUserId, Integer shopId, Integer employeeId);
}