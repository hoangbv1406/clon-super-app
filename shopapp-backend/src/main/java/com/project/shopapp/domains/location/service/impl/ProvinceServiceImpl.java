package com.project.shopapp.domains.location.service.impl;

import com.project.shopapp.domains.location.dto.response.ProvinceResponse;
import com.project.shopapp.domains.location.mapper.ProvinceMapper;
import com.project.shopapp.domains.location.repository.ProvinceRepository;
import com.project.shopapp.domains.location.service.ProvinceService;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "provinces", key = "'all_active'") // Lưu cache Redis
    public List<ProvinceResponse> getAllActiveProvinces() {
        return provinceRepository.findAll().stream()
                .filter(p -> Boolean.TRUE.equals(p.getIsActive()))
                .map(provinceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "province", key = "#code")
    public ProvinceResponse getProvinceByCode(String code) {
        var province = provinceRepository.findByCodeAndIsActiveTrue(code)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy Tỉnh/Thành phố hoặc đã bị vô hiệu hóa"));
        return provinceMapper.toDto(province);
    }
}