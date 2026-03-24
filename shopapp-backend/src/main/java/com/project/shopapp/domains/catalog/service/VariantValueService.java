package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.response.VariantMatrixResponse;
import java.util.List;

public interface VariantValueService {
    List<VariantMatrixResponse> getMatrixByProduct(Integer productId);
}