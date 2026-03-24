package com.project.shopapp.domains.identity.api;

import com.project.shopapp.domains.identity.dto.nested.UserAddressBasicDto;

public interface UserAddressInternalApi {
    /**
     * Lấy chuỗi địa chỉ đầy đủ dựa trên ID
     */
    UserAddressBasicDto getFullAddressString(Integer addressId);

    /**
     * Check xem addressId này có thực sự thuộc về userId này không (chống IDOR khi Checkout)
     */
    boolean verifyAddressBelongsToUser(Integer addressId, Integer userId);
}