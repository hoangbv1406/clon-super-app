package com.project.shopapp.domains.identity.api.impl;

import com.project.shopapp.domains.identity.api.UserAddressInternalApi;
import com.project.shopapp.domains.identity.dto.nested.UserAddressBasicDto;
import com.project.shopapp.domains.identity.entity.UserAddress;
import com.project.shopapp.domains.identity.repository.UserAddressRepository;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAddressInternalApiImpl implements UserAddressInternalApi {

    private final UserAddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public UserAddressBasicDto getFullAddressString(Integer addressId) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy địa chỉ giao hàng"));

        // Format: Tên người nhận - Số điện thoại - Địa chỉ chi tiết, Xã, Huyện, Tỉnh
        String fullString = String.format("%s - %s - %s, %s, %s, %s",
                address.getRecipientName(),
                address.getPhoneNumber(),
                address.getAddressDetail(),
                address.getWard() != null ? address.getWard().getName() : "",
                address.getDistrict() != null ? address.getDistrict().getName() : "",
                address.getProvince() != null ? address.getProvince().getName() : ""
        );

        UserAddressBasicDto dto = new UserAddressBasicDto();
        dto.setId(address.getId());
        dto.setRecipientName(address.getRecipientName());
        dto.setFullAddressString(fullString);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyAddressBelongsToUser(Integer addressId, Integer userId) {
        return addressRepository.findById(addressId)
                .map(address -> address.getUserId().equals(userId) && !address.getIsDeleted())
                .orElse(false);
    }
}