package com.project.shopapp.domains.identity.service;
import com.project.shopapp.domains.identity.dto.request.UserAddressRequest;
import com.project.shopapp.domains.identity.dto.response.UserAddressResponse;
import java.util.List;

public interface UserAddressService {
    List<UserAddressResponse> getMyAddresses(Integer userId);
    UserAddressResponse addAddress(Integer userId, UserAddressRequest request);
    UserAddressResponse updateAddress(Integer userId, Integer addressId, UserAddressRequest request);
    void deleteAddress(Integer userId, Integer addressId);
}