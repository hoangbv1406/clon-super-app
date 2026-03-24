package com.project.shopapp.domains.location.service.impl;

import com.project.shopapp.domains.location.dto.response.DistrictResponse;
import com.project.shopapp.domains.location.mapper.DistrictMapper;
import com.project.shopapp.domains.location.repository.DistrictRepository;
import com.project.shopapp.domains.location.service.DistrictService;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "districts_by_province", key = "#provinceCode") // Load huyện theo mã tỉnh
    public List<DistrictResponse> getActiveDistrictsByProvince(String provinceCode) {
        return districtRepository.findByProvinceCodeAndIsActiveTrue(provinceCode)
                .stream()
                .map(districtMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "district_detail", key = "#code")
    public DistrictResponse getDistrictByCode(String code) {
        var district = districtRepository.findByCodeAndIsActiveTrue(code)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy Quận/Huyện"));
        return districtMapper.toDto(district);
    }
}