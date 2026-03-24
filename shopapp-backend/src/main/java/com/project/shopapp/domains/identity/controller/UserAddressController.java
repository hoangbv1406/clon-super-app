package com.project.shopapp.domains.identity.controller;

import com.project.shopapp.domains.identity.dto.request.UserAddressRequest;
import com.project.shopapp.domains.identity.dto.response.UserAddressResponse;
import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.identity.service.UserAddressService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity/addresses")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService addressService;
    private final SecurityUtils securityUtils;

    @GetMapping
    public ResponseEntity<ResponseObject<List<UserAddressResponse>>> getMyAddresses() {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(addressService.getMyAddresses(userId)));
    }

    @PostMapping
    public ResponseEntity<ResponseObject<UserAddressResponse>> addAddress(@Valid @RequestBody UserAddressRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                addressService.addAddress(userId, request), "Thêm mới sổ địa chỉ thành công"
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject<UserAddressResponse>> updateAddress(
            @PathVariable Integer id,
            @Valid @RequestBody UserAddressRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(
                addressService.updateAddress(userId, id, request), "Cập nhật địa chỉ thành công"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject<Void>> deleteAddress(@PathVariable Integer id) {
        Integer userId = securityUtils.getLoggedInUserId();
        addressService.deleteAddress(userId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa địa chỉ thành công"));
    }
}