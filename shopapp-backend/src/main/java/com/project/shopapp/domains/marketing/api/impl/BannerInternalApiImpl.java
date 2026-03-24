// --- api/impl/BannerInternalApiImpl.java ---
package com.project.shopapp.domains.marketing.api.impl;
import com.project.shopapp.domains.marketing.api.BannerInternalApi;
import com.project.shopapp.domains.marketing.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerInternalApiImpl implements BannerInternalApi {
    private final BannerRepository bannerRepo;

    @Override
    public String getTargetUrl(Integer bannerId) {
        return bannerRepo.findById(bannerId).map(b -> b.getTargetUrl()).orElse(null);
    }
}