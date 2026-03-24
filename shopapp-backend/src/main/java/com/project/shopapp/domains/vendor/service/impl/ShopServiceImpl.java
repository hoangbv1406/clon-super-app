package com.project.shopapp.domains.vendor.service.impl;

import com.project.shopapp.domains.vendor.dto.request.ShopRegistrationRequest;
import com.project.shopapp.domains.vendor.dto.request.ShopStatusUpdateRequest;
import com.project.shopapp.domains.vendor.dto.response.ShopResponse;
import com.project.shopapp.domains.vendor.entity.Shop;
import com.project.shopapp.domains.vendor.enums.ShopStatus;
import com.project.shopapp.domains.vendor.event.ShopStatusChangedEvent;
import com.project.shopapp.domains.vendor.mapper.ShopMapper;
import com.project.shopapp.domains.vendor.repository.ShopRepository;
import com.project.shopapp.domains.vendor.service.ShopService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ShopResponse registerShop(Integer ownerId, ShopRegistrationRequest request) {
        // Một user chỉ được mở 1 shop (Mô hình Shopee)
        if (shopRepository.findByOwnerIdAndIsDeleted(ownerId, 0L).isPresent()) {
            throw new ConflictException("Bạn đã đăng ký một gian hàng rồi, không thể tạo thêm.");
        }

        String baseSlug = SlugUtils.toSlug(request.getName());
        String finalSlug = baseSlug;
        int counter = 1;

        // Đảm bảo slug không bị trùng
        while (shopRepository.existsBySlugAndIsDeleted(finalSlug, 0L)) {
            finalSlug = baseSlug + "-" + counter++;
        }

        Shop shop = shopMapper.toEntityFromRequest(request);
        shop.setOwnerId(ownerId);
        shop.setSlug(finalSlug);
        shop.setStatus(ShopStatus.PENDING); // Bắt buộc chờ Admin duyệt

        return shopMapper.toDto(shopRepository.save(shop));
    }

    @Override
    @Transactional(readOnly = true)
    public ShopResponse getShopBySlug(String slug) {
        Shop shop = shopRepository.findBySlugAndIsDeleted(slug, 0L)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy gian hàng"));

        // Chỉ hiện thông tin nếu shop đang ACTIVE, hoặc admin xem
        if (shop.getStatus() != ShopStatus.ACTIVE) {
            throw new DataNotFoundException("Gian hàng này hiện đang bị khóa hoặc chưa được duyệt.");
        }
        return shopMapper.toDto(shop);
    }

    @Override
    @Transactional
    public ShopResponse updateShopStatus(Integer adminId, Integer shopId, ShopStatusUpdateRequest request) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy gian hàng"));

        ShopStatus oldStatus = shop.getStatus();
        ShopStatus newStatus = ShopStatus.valueOf(request.getStatus().toUpperCase());

        if (oldStatus != newStatus) {
            shop.setStatus(newStatus);
            shop.setUpdatedBy(adminId);
            shopRepository.save(shop);

            // Bắn event cực kỳ quan trọng để ẩn SP nếu bị BAN, hoặc bắn mail chúc mừng nếu ACTIVE
            eventPublisher.publishEvent(new ShopStatusChangedEvent(shop.getId(), oldStatus, newStatus, request.getReason()));
        }

        return shopMapper.toDto(shop);
    }
}