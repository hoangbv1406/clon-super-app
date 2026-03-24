package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.nested.PriceChartPointDto;
import com.project.shopapp.domains.catalog.dto.response.PriceHistoryResponse;
import com.project.shopapp.domains.catalog.entity.PriceHistory;
import com.project.shopapp.domains.catalog.mapper.PriceHistoryMapper;
import com.project.shopapp.domains.catalog.repository.PriceHistoryRepository;
import com.project.shopapp.domains.catalog.service.PriceHistoryService;
import com.project.shopapp.domains.catalog.specification.PriceHistorySpecification;
import com.project.shopapp.shared.base.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final PriceHistoryRepository historyRepo;
    private final PriceHistoryMapper historyMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PriceHistoryResponse> getPriceLogs(Integer productId, int page, int size) {
        Page<PriceHistory> pagedResult = historyRepo.findAll(
                PriceHistorySpecification.filterByDateRange(productId, null, null),
                PageRequest.of(page - 1, size, Sort.by("createdAt").descending())
        );
        return PageResponse.of(pagedResult.map(historyMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "price_charts", key = "#productId + '_' + (#variantId != null ? #variantId : '0')")
    public List<PriceChartPointDto> getPriceChart(Integer productId, Integer variantId) {
        List<PriceHistory> histories;
        if (variantId != null) {
            histories = historyRepo.findByProductIdAndVariantIdOrderByCreatedAtAsc(productId, variantId);
        } else {
            histories = historyRepo.findByProductIdAndVariantIdIsNullOrderByCreatedAtAsc(productId);
        }

        // Chuyển đổi dữ liệu thô thành các điểm vẽ biểu đồ (Ngày - Giá)
        return histories.stream()
                .map(h -> new PriceChartPointDto(h.getCreatedAt().toLocalDate(), h.getNewPrice()))
                .collect(Collectors.toList());
    }
}