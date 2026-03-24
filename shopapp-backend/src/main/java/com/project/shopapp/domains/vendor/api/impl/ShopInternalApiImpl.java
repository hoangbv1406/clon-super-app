package com.project.shopapp.domains.vendor.api.impl;
import com.project.shopapp.domains.vendor.api.ShopInternalApi;
import com.project.shopapp.domains.vendor.dto.nested.ShopBasicDto;
import com.project.shopapp.domains.vendor.enums.ShopStatus;
import com.project.shopapp.domains.vendor.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShopInternalApiImpl implements ShopInternalApi {
    private final ShopRepository shopRepository;

    @Override
    @Transactional(readOnly = true)
    public ShopBasicDto getShopBasicInfo(Integer shopId) {
        return shopRepository.findById(shopId).filter(s -> s.getIsDeleted() == 0L)
                .map(s -> {
                    ShopBasicDto dto = new ShopBasicDto();
                    dto.setId(s.getId());
                    dto.setName(s.getName());
                    dto.setSlug(s.getSlug());
                    dto.setLogoUrl(s.getLogoUrl());
                    dto.setRatingAvg(s.getRatingAvg());
                    return dto;
                }).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getShopCommissionRate(Integer shopId) {
        return shopRepository.findById(shopId).map(s -> s.getCommissionRate()).orElse(BigDecimal.ZERO);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isShopActive(Integer shopId) {
        return shopRepository.findById(shopId)
                .map(s -> s.getStatus() == ShopStatus.ACTIVE && s.getIsDeleted() == 0L)
                .orElse(false);
    }

    @Override
    @Transactional
    public void incrementShopOrderCount(Integer shopId) {
        shopRepository.incrementTotalOrders(shopId); // Tối ưu Locking
    }
}