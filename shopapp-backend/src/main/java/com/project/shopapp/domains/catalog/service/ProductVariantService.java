package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.ProductVariantCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.ProductVariantResponse;
import java.util.List;

public interface ProductVariantService {
    List<ProductVariantResponse> getVariantsOfProduct(Integer productId);
    ProductVariantResponse createVariant(Integer adminId, ProductVariantCreateRequest request);
}