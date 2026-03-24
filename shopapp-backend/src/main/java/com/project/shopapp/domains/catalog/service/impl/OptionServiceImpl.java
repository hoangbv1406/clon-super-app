package com.project.shopapp.domains.catalog.service.impl;

import com.project.shopapp.domains.catalog.dto.request.OptionCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionResponse;
import com.project.shopapp.domains.catalog.entity.Option;
import com.project.shopapp.domains.catalog.event.OptionStatusChangedEvent;
import com.project.shopapp.domains.catalog.mapper.OptionMapper;
import com.project.shopapp.domains.catalog.repository.OptionRepository;
import com.project.shopapp.domains.catalog.service.OptionService;
import com.project.shopapp.domains.catalog.specification.OptionSpecification;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "master_data_options", key = "'all_active'")
    public List<OptionResponse> getAllActiveOptions() {
        return optionRepository.findByIsActiveTrueAndIsDeleted(0L)
                .stream().map(optionMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OptionResponse> searchOptions(String keyword, int page, int size) {
        Page<Option> optionPage = optionRepository.findAll(
                OptionSpecification.search(keyword),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(optionPage.map(optionMapper::toDto));
    }

    @Override
    @Transactional
    @CacheEvict(value = "master_data_options", allEntries = true) // Xóa cache khi thêm mới
    public OptionResponse createOption(Integer adminId, OptionCreateRequest request) {
        if (optionRepository.existsByCodeAndIsDeleted(request.getCode(), 0L)) {
            throw new ConflictException("Mã Tùy chọn này đã tồn tại trong hệ thống.");
        }

        Option option = optionMapper.toEntityFromRequest(request);
        option.setCreatedBy(adminId);

        return optionMapper.toDto(optionRepository.save(option));
    }

    @Override
    @Transactional
    @CacheEvict(value = "master_data_options", allEntries = true)
    public OptionResponse toggleOptionStatus(Integer adminId, Integer id, boolean isActive) {
        Option option = optionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Tùy chọn không tồn tại"));

        if (option.getIsActive() != isActive) {
            option.setIsActive(isActive);
            option.setUpdatedBy(adminId);
            optionRepository.save(option);

            // Bắn event để block các Shop cố tình dùng option đã bị khóa
            eventPublisher.publishEvent(new OptionStatusChangedEvent(id, isActive));
        }

        return optionMapper.toDto(option);
    }
}