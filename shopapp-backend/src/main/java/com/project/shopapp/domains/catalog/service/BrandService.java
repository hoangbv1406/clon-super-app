package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.BrandCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.BrandResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface BrandService {
    PageResponse<BrandResponse> getAllBrands(String keyword, String tier, Boolean isActive, int page, int size);
    BrandResponse createBrand(BrandCreateRequest request);
    BrandResponse updateBrandStatus(Integer id, boolean isActive);
    void deleteBrand(Integer id);
}