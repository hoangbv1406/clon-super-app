package com.project.shopapp.domains.catalog.api;
import java.util.List;

public interface VariantValueInternalApi {
    void syncVariantMatrix(Integer productId, Integer variantId, List<Integer> optionValueIds);
}