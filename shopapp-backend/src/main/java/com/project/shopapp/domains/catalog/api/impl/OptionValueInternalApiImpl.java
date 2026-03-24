// --- api/impl/OptionValueInternalApiImpl.java ---
package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.OptionValueInternalApi;
import com.project.shopapp.domains.catalog.entity.OptionValue;
import com.project.shopapp.domains.catalog.repository.OptionValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OptionValueInternalApiImpl implements OptionValueInternalApi {

    private final OptionValueRepository optionValueRepo;

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> generateAttributesMap(List<Integer> optionValueIds) {
        List<OptionValue> values = optionValueRepo.findAllById(optionValueIds);
        Map<String, String> attributes = new HashMap<>();

        for (OptionValue val : values) {
            // Lấy tên Option (Màu) làm Key, giá trị (Đỏ) làm Value
            attributes.put(val.getOption().getName(), val.getValue());
        }
        return attributes;
    }
}