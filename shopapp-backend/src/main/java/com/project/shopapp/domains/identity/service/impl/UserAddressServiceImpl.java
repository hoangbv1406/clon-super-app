package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.dto.request.UserAddressRequest;
import com.project.shopapp.domains.identity.dto.response.UserAddressResponse;
import com.project.shopapp.domains.identity.entity.UserAddress;
import com.project.shopapp.domains.identity.event.DefaultAddressChangedEvent;
import com.project.shopapp.domains.identity.mapper.UserAddressMapper;
import com.project.shopapp.domains.identity.repository.UserAddressRepository;
import com.project.shopapp.domains.identity.service.UserAddressService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository addressRepository;
    private final UserAddressMapper addressMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public List<UserAddressResponse> getMyAddresses(Integer userId) {
        return addressRepository.findByUserIdAndIsDeletedFalseOrderByIsDefaultDesc(userId)
                .stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserAddressResponse addAddress(Integer userId, UserAddressRequest request) {
        // Chống spam: Mỗi user tối đa 10 địa chỉ
        if (addressRepository.countByUserIdAndIsDeletedFalse(userId) >= 10) {
            throw new ConflictException("Bạn chỉ được lưu tối đa 10 sổ địa chỉ.");
        }

        UserAddress address = addressMapper.toEntityFromRequest(request);
        address.setUserId(userId);

        // Nếu đây là địa chỉ đầu tiên, hoặc user tick là mặc định
        if (addressRepository.countByUserIdAndIsDeletedFalse(userId) == 0 || Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.resetDefaultAddress(userId);
            address.setIsDefault(true);
            // Bắn event Anti-Fraud
            eventPublisher.publishEvent(new DefaultAddressChangedEvent(userId, request.getProvinceCode(), request.getDistrictCode()));
        }

        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    @Transactional
    public UserAddressResponse updateAddress(Integer userId, Integer addressId, UserAddressRequest request) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy địa chỉ"));

        if (!address.getUserId().equals(userId)) {
            throw new ForbiddenException("Bạn không có quyền sửa địa chỉ này");
        }

        // Nếu đổi thành mặc định
        if (Boolean.TRUE.equals(request.getIsDefault()) && !Boolean.TRUE.equals(address.getIsDefault())) {
            addressRepository.resetDefaultAddress(userId);
            eventPublisher.publishEvent(new DefaultAddressChangedEvent(userId, request.getProvinceCode(), request.getDistrictCode()));
        }

        addressMapper.updateEntityFromRequest(request, address);
        address.setUpdatedAt(LocalDateTime.now());

        return addressMapper.toDto(addressRepository.save(address));
    }

    @Override
    @Transactional
    public void deleteAddress(Integer userId, Integer addressId) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy địa chỉ"));

        if (!address.getUserId().equals(userId)) {
            throw new ForbiddenException("Bạn không có quyền xóa địa chỉ này");
        }

        address.setIsDeleted(true); // Soft Delete
        addressRepository.save(address);

        // Nếu xóa trúng địa chỉ Mặc định, cần có logic lấy địa chỉ cũ nhất làm mặc định (Option mở rộng)
    }
}