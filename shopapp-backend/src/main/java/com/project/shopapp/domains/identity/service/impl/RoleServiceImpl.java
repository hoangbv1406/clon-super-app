package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.dto.request.RoleRequest;
import com.project.shopapp.domains.identity.dto.response.RoleResponse;
import com.project.shopapp.domains.identity.entity.Role;
import com.project.shopapp.domains.identity.event.RoleStatusChangedEvent;
import com.project.shopapp.domains.identity.mapper.RoleMapper;
import com.project.shopapp.domains.identity.repository.RoleRepository;
import com.project.shopapp.domains.identity.service.RoleService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "roles", key = "'all'") // Caching siêu quan trọng ở đây
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "roles", allEntries = true) // Xóa toàn bộ cache list role
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new ConflictException("Tên Role đã tồn tại trong hệ thống");
        }
        Role role = roleMapper.toEntityFromRequest(request);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    @CacheEvict(value = "roles", allEntries = true)
    public RoleResponse updateRoleStatus(Integer id, boolean isActive) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy Role"));

        if (role.getIsActive() != isActive) {
            role.setIsActive(isActive);
            roleRepository.save(role);
            // Bắn event để thu hồi JWT Token của những user mang role này
            eventPublisher.publishEvent(new RoleStatusChangedEvent(role.getId(), role.getName(), isActive));
        }
        return roleMapper.toDto(role);
    }
}