package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.ProductCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface ProductService {
    PageResponse<ProductResponse> searchProducts(String keyword, Integer categoryId, Integer brandId, String vRam, int page, int size);
    ProductResponse getProductBySlug(String slug);
    ProductResponse createProduct(Integer currentUserId, ProductCreateRequest request);
}