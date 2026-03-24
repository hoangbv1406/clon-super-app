package com.project.shopapp.domains.vendor.api;
import com.project.shopapp.domains.vendor.dto.nested.ShopBasicDto;
import java.math.BigDecimal;

public interface ShopInternalApi {
    ShopBasicDto getShopBasicInfo(Integer shopId);
    BigDecimal getShopCommissionRate(Integer shopId);
    boolean isShopActive(Integer shopId);
    void incrementShopOrderCount(Integer shopId);
}