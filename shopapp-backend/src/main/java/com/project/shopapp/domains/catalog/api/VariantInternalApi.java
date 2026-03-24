package com.project.shopapp.domains.catalog.api;
import com.project.shopapp.domains.catalog.dto.nested.ProductVariantBasicDto;

public interface VariantInternalApi {
    ProductVariantBasicDto getVariantBasicInfo(Integer variantId);
    boolean lockStock(Integer variantId, Integer qty);
    void unlockStock(Integer variantId, Integer qty);
    void commitStock(Integer variantId, Integer qty);
}