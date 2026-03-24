// --- api/impl/OptionInternalApiImpl.java ---
package com.project.shopapp.domains.catalog.api.impl;
import com.project.shopapp.domains.catalog.api.OptionInternalApi;
import com.project.shopapp.domains.catalog.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionInternalApiImpl implements OptionInternalApi {

    private final OptionRepository optionRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean areOptionsValidAndActive(List<Integer> optionIds) {
        if (optionIds == null || optionIds.isEmpty()) return true;
        // Đếm số lượng ID hợp lệ trong DB, nếu bằng kích thước list đầu vào nghĩa là tất cả đều valid
        long validCount = optionRepository.findAllById(optionIds).stream()
                .filter(opt -> opt.getIsActive() && opt.getIsDeleted() == 0L)
                .count();
        return validCount == optionIds.size();
    }
}