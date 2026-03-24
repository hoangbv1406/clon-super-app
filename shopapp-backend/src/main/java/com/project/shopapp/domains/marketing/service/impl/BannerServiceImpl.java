package com.project.shopapp.domains.marketing.service.impl;

import com.project.shopapp.domains.marketing.dto.request.BannerCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.BannerResponse;
import com.project.shopapp.domains.marketing.entity.Banner;
import com.project.shopapp.domains.marketing.enums.BannerPlatform;
import com.project.shopapp.domains.marketing.enums.BannerPosition;
import com.project.shopapp.domains.marketing.event.BannerClickedEvent;
import com.project.shopapp.domains.marketing.mapper.BannerMapper;
import com.project.shopapp.domains.marketing.repository.BannerRepository;
import com.project.shopapp.domains.marketing.service.BannerService;
import com.project.shopapp.domains.marketing.specification.BannerSpecification;
import com.project.shopapp.shared.base.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepo;
    private final BannerMapper bannerMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "active_banners", key = "#position + '_' + #platform") // Cache bắt buộc
    public List<BannerResponse> getActiveBanners(String position, String platform) {
        BannerPosition posEnum = BannerPosition.valueOf(position.toUpperCase());
        BannerPlatform platEnum = BannerPlatform.valueOf(platform.toUpperCase());

        return bannerRepo.findActiveBanners(posEnum, platEnum, LocalDateTime.now())
                .stream().map(bannerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void trackClick(Integer bannerId) {
        // Chỉ phát Event, không Block thread của client
        eventPublisher.publishEvent(new BannerClickedEvent(bannerId));
    }

    // LISTENER XỬ LÝ CLICK CHẠY NGẦM
    @Async("marketingTaskExecutor")
    @EventListener
    @Transactional
    public void handleBannerClick(BannerClickedEvent event) {
        bannerRepo.incrementClickCount(event.getBannerId());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BannerResponse> searchBannersForAdmin(String title, String position, int page, int size) {
        Page<Banner> pagedResult = bannerRepo.findAll(
                BannerSpecification.filterForAdmin(title, position),
                PageRequest.of(page - 1, size, Sort.by("id").descending())
        );
        return PageResponse.of(pagedResult.map(bannerMapper::toDto));
    }

    @Override
    @Transactional
    @CacheEvict(value = "active_banners", allEntries = true) // Xóa Cache khi Admin đổi Banner
    public BannerResponse createBanner(Integer adminId, BannerCreateRequest request) {
        Banner banner = bannerMapper.toEntityFromRequest(request);

        if (request.getPosition() != null) banner.setPosition(BannerPosition.valueOf(request.getPosition()));
        if (request.getPlatform() != null) banner.setPlatform(BannerPlatform.valueOf(request.getPlatform()));

        banner.setCreatedBy(adminId);
        return bannerMapper.toDto(bannerRepo.save(banner));
    }

    @Override
    @Transactional
    @CacheEvict(value = "active_banners", allEntries = true)
    public void deleteBanner(Integer adminId, Integer bannerId) {
        Banner banner = bannerRepo.findById(bannerId).orElseThrow();
        banner.setIsDeleted(System.currentTimeMillis());
        banner.setIsActive(false);
        banner.setUpdatedBy(adminId);
        bannerRepo.save(banner);
    }
}