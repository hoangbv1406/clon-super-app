package com.project.shopapp.domains.marketing.service.impl;

import com.project.shopapp.domains.marketing.dto.request.CouponCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.CouponResponse;
import com.project.shopapp.domains.marketing.entity.Coupon;
import com.project.shopapp.domains.marketing.enums.CouponDiscountType;
import com.project.shopapp.domains.marketing.mapper.CouponMapper;
import com.project.shopapp.domains.marketing.repository.CouponRepository;
import com.project.shopapp.domains.marketing.service.CouponService;
import com.project.shopapp.domains.marketing.specification.CouponSpecification;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepo;
    private final CouponMapper couponMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "available_coupons", key = "#shopId != null ? #shopId : 'platform'")
    public List<CouponResponse> getAvailableCouponsForShop(Integer shopId) {
        return couponRepo.findAll(CouponSpecification.getAvailableCoupons(shopId, LocalDateTime.now()))
                .stream().map(couponMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "available_coupons", allEntries = true)
    public CouponResponse createCoupon(Integer currentUserId, Integer shopId, CouponCreateRequest request) {
        if (couponRepo.existsByCodeAndIsDeleted(request.getCode().toUpperCase(), 0L)) {
            throw new ConflictException("Mã Voucher này đã tồn tại trên hệ thống!");
        }

        Coupon coupon = couponMapper.toEntityFromRequest(request);
        coupon.setCode(request.getCode().toUpperCase());
        coupon.setShopId(shopId); // NULL nếu là Admin tạo cho Platform
        coupon.setDiscountType(CouponDiscountType.valueOf(request.getDiscountType().toUpperCase()));
        coupon.setCreatedBy(currentUserId);

        return couponMapper.toDto(couponRepo.save(coupon));
    }

    @Override
    @Transactional
    @CacheEvict(value = "available_coupons", allEntries = true)
    public void toggleCouponStatus(Integer currentUserId, Integer couponId, boolean isActive) {
        Coupon coupon = couponRepo.findById(couponId)
                .orElseThrow(() -> new DataNotFoundException("Voucher không tồn tại"));

        coupon.setIsActive(isActive);
        coupon.setUpdatedBy(currentUserId);
        couponRepo.save(coupon);
    }
}