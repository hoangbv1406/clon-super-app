package com.project.shopapp.domains.catalog.api;
import com.project.shopapp.domains.catalog.dto.nested.BrandBasicDto;

public interface BrandInternalApi {
    boolean isBrandActiveAndValid(Integer brandId);
    BrandBasicDto getBrandBasicInfo(Integer brandId);
}