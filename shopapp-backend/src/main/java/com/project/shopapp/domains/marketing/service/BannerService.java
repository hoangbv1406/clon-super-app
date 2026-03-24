package com.project.shopapp.domains.marketing.service;
import com.project.shopapp.domains.marketing.dto.request.BannerCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.BannerResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.util.List;

public interface BannerService {
    // API Public (Siêu nhanh - Có Cache)
    List<BannerResponse> getActiveBanners(String position, String platform);

    // API Async tracking
    void trackClick(Integer bannerId);

    // API Admin CMS
    PageResponse<BannerResponse> searchBannersForAdmin(String title, String position, int page, int size);
    BannerResponse createBanner(Integer adminId, BannerCreateRequest request);
    void deleteBanner(Integer adminId, Integer bannerId);
}