// --- service/impl/VariantValueServiceImpl.java ---
package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.response.VariantMatrixResponse;
import com.project.shopapp.domains.catalog.repository.VariantValueRepository;
import com.project.shopapp.domains.catalog.service.VariantValueService;
import com.project.shopapp.domains.catalog.specification.VariantValueSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VariantValueServiceImpl implements VariantValueService {

    private final VariantValueRepository matrixRepo;

    @Override
    @Transactional(readOnly = true)
    public List<VariantMatrixResponse> getMatrixByProduct(Integer productId) {
        // Query toàn bộ matrix của 1 Product để Admin xem
        return matrixRepo.findAll(VariantValueSpecification.filterMatrix(productId, null))
                .stream().map(matrix -> VariantMatrixResponse.builder()
                        .variantId(matrix.getVariantId())
                        .optionName(matrix.getOptionValue().getOption().getName()) // Lazy load
                        .valueName(matrix.getOptionValue().getValue())
                        .metaData(matrix.getOptionValue().getMetaData())
                        .build())
                .collect(Collectors.toList());
    }
}