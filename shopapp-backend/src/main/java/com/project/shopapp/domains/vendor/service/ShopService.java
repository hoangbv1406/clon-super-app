package com.project.shopapp.domains.vendor.service;
import com.project.shopapp.domains.vendor.dto.request.ShopRegistrationRequest;
import com.project.shopapp.domains.vendor.dto.request.ShopStatusUpdateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopResponse;

public interface ShopService {
    ShopResponse registerShop(Integer ownerId, ShopRegistrationRequest request);
    ShopResponse getShopBySlug(String slug);
    ShopResponse updateShopStatus(Integer adminId, Integer shopId, ShopStatusUpdateRequest request);
}