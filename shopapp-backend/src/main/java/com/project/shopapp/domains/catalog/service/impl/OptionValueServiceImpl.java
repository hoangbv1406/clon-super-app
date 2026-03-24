package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.request.OptionValueCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionValueResponse;
import com.project.shopapp.domains.catalog.entity.OptionValue;
import com.project.shopapp.domains.catalog.mapper.OptionValueMapper;
import com.project.shopapp.domains.catalog.repository.OptionRepository;
import com.project.shopapp.domains.catalog.repository.OptionValueRepository;
import com.project.shopapp.domains.catalog.service.OptionValueService;
import com.project.shopapp.domains.catalog.specification.OptionValueSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
public class OptionValueServiceImpl implements OptionValueService {

    private final OptionValueRepository valueRepository;
    private final OptionRepository optionRepository;
    private final OptionValueMapper valueMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "option_values", key = "#optionId")
    public List<OptionValueResponse> getActiveValuesByOption(Integer optionId) {
        return valueRepository.findByOptionIdAndIsActiveTrueAndIsDeletedOrderByDisplayOrderAsc(optionId, 0L)
                .stream().map(valueMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OptionValueResponse> getValuesForAdmin(Integer optionId, String keyword, int page, int size) {
        Page<OptionValue> pagedResult = valueRepository.findAll(
                OptionValueSpecification.filterByOptionAndKeyword(optionId, keyword),
                PageRequest.of(page - 1, size, Sort.by("displayOrder").ascending())
        );
        return PageResponse.of(pagedResult.map(valueMapper::toDto));
    }

    @Override
    @Transactional
    @CacheEvict(value = "option_values", key = "#request.optionId")
    public OptionValueResponse createOptionValue(Integer adminId, OptionValueCreateRequest request) {
        if (!optionRepository.existsById(request.getOptionId())) {
            throw new DataNotFoundException("Tùy chọn gốc không tồn tại");
        }

        if (valueRepository.existsByOptionIdAndValueAndIsDeleted(request.getOptionId(), request.getValue(), 0L)) {
            throw new ConflictException("Giá trị '" + request.getValue() + "' đã tồn tại trong tùy chọn này.");
        }

        OptionValue optionValue = valueMapper.toEntityFromRequest(request);
        optionValue.setCreatedBy(adminId);

        return valueMapper.toDto(valueRepository.save(optionValue));
    }

    @Override
    @Transactional
    @CacheEvict(value = "option_values", key = "#result.optionId") // Xóa cache của Option liên quan
    public void deleteOptionValue(Integer adminId, Integer id) {
        OptionValue optionValue = valueRepository.findById(id).orElseThrow();

        // Thực hiện Soft Delete
        optionValue.setIsDeleted(System.currentTimeMillis());
        optionValue.setIsActive(false);
        optionValue.setUpdatedBy(adminId);

        valueRepository.save(optionValue);
        // Note: Cần check nếu OptionValue này đang được dùng ở ProductVariant nào không.
    }
}