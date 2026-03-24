package com.project.shopapp.domains.vendor.service.impl;

import com.project.shopapp.domains.vendor.api.ShopEmployeeInternalApi;
import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeCreateRequest;
import com.project.shopapp.domains.vendor.dto.request.ShopEmployeeUpdateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopEmployeeResponse;
import com.project.shopapp.domains.vendor.entity.ShopEmployee;
import com.project.shopapp.domains.vendor.enums.EmployeeStatus;
import com.project.shopapp.domains.vendor.enums.ShopEmployeeRole;
import com.project.shopapp.domains.vendor.event.ShopEmployeeResignedEvent;
import com.project.shopapp.domains.vendor.mapper.ShopEmployeeMapper;
import com.project.shopapp.domains.vendor.repository.ShopEmployeeRepository;
import com.project.shopapp.domains.vendor.service.ShopEmployeeService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopEmployeeServiceImpl implements ShopEmployeeService {

    private final ShopEmployeeRepository empRepository;
    private final ShopEmployeeMapper empMapper;
    private final ShopEmployeeInternalApi shopPermissionApi;
    private final ApplicationEventPublisher eventPublisher;

    private void verifyManagerPermission(Integer currentUserId, Integer shopId) {
        if (!shopPermissionApi.hasPermissionInShop(currentUserId, shopId, "MANAGER")) {
            throw new ForbiddenException("Chỉ Chủ shop hoặc Quản lý mới có quyền thao tác nhân sự");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopEmployeeResponse> getEmployees(Integer currentUserId, Integer shopId) {
        verifyManagerPermission(currentUserId, shopId);
        return empRepository.findByShopIdAndIsDeleted(shopId, 0L)
                .stream().map(empMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "shop_permissions", key = "#shopId + '_' + #request.userId")
    public ShopEmployeeResponse addEmployee(Integer currentUserId, Integer shopId, ShopEmployeeCreateRequest request) {
        verifyManagerPermission(currentUserId, shopId);

        if (empRepository.findByShopIdAndUserIdAndIsDeleted(shopId, request.getUserId(), 0L).isPresent()) {
            throw new ConflictException("User này đã là nhân viên của shop");
        }

        ShopEmployee employee = empMapper.toEntityFromCreateRequest(request);
        employee.setShopId(shopId);
        employee.setCreatedBy(currentUserId);

        return empMapper.toDto(empRepository.save(employee));
    }

    @Override
    @Transactional
    @CacheEvict(value = "shop_permissions", key = "#shopId + '_' + #result.userId")
    public ShopEmployeeResponse updateEmployee(Integer currentUserId, Integer shopId, Integer employeeId, ShopEmployeeUpdateRequest request) {
        verifyManagerPermission(currentUserId, shopId);

        ShopEmployee employee = empRepository.findById(employeeId)
                .filter(e -> e.getIsDeleted() == 0L && e.getShopId().equals(shopId))
                .orElseThrow(() -> new DataNotFoundException("Nhân viên không tồn tại"));

        EmployeeStatus oldStatus = employee.getStatus();

        if (request.getRole() != null) employee.setRole(ShopEmployeeRole.valueOf(request.getRole()));
        if (request.getStatus() != null) employee.setStatus(EmployeeStatus.valueOf(request.getStatus()));

        employee.setUpdatedBy(currentUserId);
        ShopEmployee saved = empRepository.save(employee);

        // KÍCH HOẠT SỰ KIỆN NẾU SA THẢI NHÂN VIÊN
        if (oldStatus == EmployeeStatus.ACTIVE && saved.getStatus() == EmployeeStatus.RESIGNED) {
            eventPublisher.publishEvent(new ShopEmployeeResignedEvent(shopId, saved.getUserId()));
        }

        return empMapper.toDto(saved);
    }

    @Override
    @Transactional
    @CacheEvict(value = "shop_permissions", key = "#shopId + '_' + #userId") // Note: logic evict cần check lại
    public void removeEmployee(Integer currentUserId, Integer shopId, Integer employeeId) {
        verifyManagerPermission(currentUserId, shopId);
        ShopEmployee employee = empRepository.findById(employeeId).orElseThrow();

        employee.setIsDeleted(System.currentTimeMillis()); // Soft Delete dứt điểm
        employee.setUpdatedBy(currentUserId);
        empRepository.save(employee);

        eventPublisher.publishEvent(new ShopEmployeeResignedEvent(shopId, employee.getUserId()));
    }
}