package com.project.shopapp.domains.catalog.api.impl;

import com.project.shopapp.domains.catalog.api.VariantValueInternalApi;
import com.project.shopapp.domains.catalog.entity.OptionValue;
import com.project.shopapp.domains.catalog.entity.VariantValue;
import com.project.shopapp.domains.catalog.repository.OptionValueRepository;
import com.project.shopapp.domains.catalog.repository.VariantValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VariantValueInternalApiImpl implements VariantValueInternalApi {

    private final VariantValueRepository variantValueRepo;
    private final OptionValueRepository optionValueRepo;

    @Override
    @Transactional
    public void syncVariantMatrix(Integer productId, Integer variantId, List<Integer> optionValueIds) {
        // Xóa sạch mapping cũ (nếu là update)
        variantValueRepo.deleteByVariantId(variantId);

        if (optionValueIds == null || optionValueIds.isEmpty()) return;

        // Tìm thông tin Option ID từ OptionValue ID
        List<OptionValue> optionValues = optionValueRepo.findAllById(optionValueIds);

        // Tạo mảng Entity để Batch Insert
        List<VariantValue> mappings = optionValues.stream()
                .map(optVal -> VariantValue.builder()
                        .variantId(variantId)
                        .productId(productId)
                        .optionId(optVal.getOptionId())
                        .optionValueId(optVal.getId())
                        .build())
                .collect(Collectors.toList());

        variantValueRepo.saveAll(mappings); // Hibernate sẽ tự optimize thành Batch Insert nếu đã config JDBC Batch
    }
}