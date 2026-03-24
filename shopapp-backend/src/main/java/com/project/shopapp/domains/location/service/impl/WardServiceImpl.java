package com.project.shopapp.domains.location.service.impl;

import com.project.shopapp.domains.location.dto.request.WardStatusUpdateRequest;
import com.project.shopapp.domains.location.dto.response.WardResponse;
import com.project.shopapp.domains.location.entity.Ward;
import com.project.shopapp.domains.location.enums.DeliveryStatus;
import com.project.shopapp.domains.location.event.WardDeliveryStatusChangedEvent;
import com.project.shopapp.domains.location.mapper.WardMapper;
import com.project.shopapp.domains.location.repository.WardRepository;
import com.project.shopapp.domains.location.service.WardService;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;
    private final WardMapper wardMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "wards_by_district", key = "#districtCode")
    public List<WardResponse> getActiveWardsByDistrict(String districtCode) {
        return wardRepository.findByDistrictCodeAndIsActiveTrue(districtCode)
                .stream()
                .map(wardMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    // Xóa cache của Huyện đó khi có xã bên trong bị thay đổi trạng thái
    @CacheEvict(value = "wards_by_district", key = "#result.districtCode")
    public WardResponse updateDeliveryStatus(String code, WardStatusUpdateRequest request) {
        Ward ward = wardRepository.findByCodeAndIsActiveTrue(code)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy Phường/Xã"));

        DeliveryStatus oldStatus = ward.getDeliveryStatus();
        DeliveryStatus newStatus = DeliveryStatus.valueOf(request.getDeliveryStatus());

        if (oldStatus != newStatus) {
            ward.setDeliveryStatus(newStatus);
            wardRepository.save(ward);

            // Bắn Event cho hệ thống biết
            eventPublisher.publishEvent(new WardDeliveryStatusChangedEvent(
                    ward.getCode(), oldStatus, newStatus, request.getReason()
            ));
        }

        return wardMapper.toDto(ward);
    }
}