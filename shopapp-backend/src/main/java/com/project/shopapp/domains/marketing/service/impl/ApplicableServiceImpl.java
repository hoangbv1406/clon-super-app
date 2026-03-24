// --- service/impl/ApplicableServiceImpl.java ---
package com.project.shopapp.domains.marketing.service.impl;

import com.project.shopapp.domains.catalog.api.BrandInternalApi;
import com.project.shopapp.domains.catalog.api.CategoryInternalApi;
import com.project.shopapp.domains.catalog.api.ProductInternalApi;
import com.project.shopapp.domains.marketing.dto.request.ApplicableCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.ApplicableResponse;
import com.project.shopapp.domains.marketing.entity.Coupon;
import com.project.shopapp.domains.marketing.entity.CouponApplicable;
import com.project.shopapp.domains.marketing.enums.ApplicableObjectType;
import com.project.shopapp.domains.marketing.enums.ApplicableRuleType;
import com.project.shopapp.domains.marketing.event.CouponRulesChangedEvent;
import com.project.shopapp.domains.marketing.mapper.ApplicableMapper;
import com.project.shopapp.domains.marketing.repository.CouponApplicableRepository;
import com.project.shopapp.domains.marketing.repository.CouponRepository;
import com.project.shopapp.domains.marketing.service.ApplicableService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicableServiceImpl implements ApplicableService {

    private final CouponApplicableRepository applicableRepo;
    private final CouponRepository couponRepo;
    private final ApplicableMapper applicableMapper;
    private final ApplicationEventPublisher eventPublisher;

    // Gọi chéo Module để Resolve Tên hiển thị (Tránh JOIN)
    private final ProductInternalApi productApi;
    private final CategoryInternalApi categoryApi;
    private final BrandInternalApi brandApi;

    @Override
    @Transactional(readOnly = true)
    public List<ApplicableResponse> getRulesForCoupon(Integer couponId) {
        return applicableRepo.findByCouponId(couponId).stream()
                .map(entity -> {
                    ApplicableResponse dto = applicableMapper.toDto(entity);
                    // Phục hồi tên bằng API Nội bộ
                    if (entity.getObjectType() == ApplicableObjectType.PRODUCT) {
                        dto.setObjectName(productApi.getProductBasicInfo(entity.getObjectId()).getName());
                    } else if (entity.getObjectType() == ApplicableObjectType.CATEGORY) {
                        dto.setObjectName(categoryApi.getCategoryBasicInfo(entity.getObjectId()).getName());
                    } else if (entity.getObjectType() == ApplicableObjectType.BRAND) {
                        dto.setObjectName(brandApi.getBrandBasicInfo(entity.getObjectId()).getName());
                    }
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApplicableResponse addRuleToCoupon(Integer adminId, Integer couponId, ApplicableCreateRequest request) {
        Coupon coupon = couponRepo.findByIdAndIsDeleted(couponId, 0L)
                .orElseThrow(() -> new DataNotFoundException("Mã giảm giá không tồn tại"));

        ApplicableObjectType objType = ApplicableObjectType.valueOf(request.getObjectType().toUpperCase());
        ApplicableRuleType ruleType = ApplicableRuleType.valueOf(request.getApplicableType().toUpperCase());

        if (applicableRepo.existsByCouponIdAndObjectTypeAndObjectIdAndApplicableType(couponId, objType, request.getObjectId(), ruleType)) {
            throw new ConflictException("Quy tắc này đã tồn tại cho mã giảm giá này");
        }

        // TODO: Cần check quyền ShopId: Shop A không được add Product của Shop B vào mã giảm giá của Shop A.

        CouponApplicable rule = applicableMapper.toEntityFromRequest(request);
        rule.setCouponId(couponId);
        rule.setObjectType(objType);
        rule.setApplicableType(ruleType);
        rule.setCreatedBy(adminId);

        CouponApplicable saved = applicableRepo.save(rule);

        eventPublisher.publishEvent(new CouponRulesChangedEvent(couponId, "ADDED"));
        return applicableMapper.toDto(saved); // Sẽ lười không set name ở POST, FE tự reload GET
    }

    @Override
    @Transactional
    public void removeRule(Integer adminId, Integer couponId, Integer ruleId) {
        CouponApplicable rule = applicableRepo.findById(ruleId).orElseThrow();
        if (!rule.getCouponId().equals(couponId)) {
            throw new ConflictException("Luật không thuộc về mã giảm giá này");
        }

        applicableRepo.delete(rule); // Bảng này xóa cứng vì nó là Metadata maping
        eventPublisher.publishEvent(new CouponRulesChangedEvent(couponId, "REMOVED"));
    }
}