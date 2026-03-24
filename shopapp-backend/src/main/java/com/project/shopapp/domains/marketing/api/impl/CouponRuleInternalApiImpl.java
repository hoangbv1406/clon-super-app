// --- api/impl/CouponRuleInternalApiImpl.java ---
package com.project.shopapp.domains.marketing.api.impl;

import com.project.shopapp.domains.marketing.api.CouponRuleInternalApi;
import com.project.shopapp.domains.marketing.entity.CouponApplicable;
import com.project.shopapp.domains.marketing.enums.ApplicableObjectType;
import com.project.shopapp.domains.marketing.enums.ApplicableRuleType;
import com.project.shopapp.domains.marketing.repository.CouponApplicableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponRuleInternalApiImpl implements CouponRuleInternalApi {

    private final CouponApplicableRepository applicableRepo;

    @Override
    @Transactional(readOnly = true)
    public boolean isProductEligibleForCoupon(Integer couponId, Integer productId, Integer categoryId, Integer brandId) {
        List<CouponApplicable> rules = applicableRepo.findByCouponId(couponId);

        // Nếu Coupon không có rule nào -> Mặc định áp dụng cho TOÀN BỘ SẢN PHẨM của Shop đó
        if (rules.isEmpty()) return true;

        // 1. DUYỆT LUẬT EXCLUDE (Quyền lực tối cao, cứ dính là chết)
        for (CouponApplicable rule : rules) {
            if (rule.getApplicableType() == ApplicableRuleType.EXCLUDE) {
                if (rule.getObjectType() == ApplicableObjectType.PRODUCT && rule.getObjectId().equals(productId)) return false;
                if (rule.getObjectType() == ApplicableObjectType.CATEGORY && rule.getObjectId().equals(categoryId)) return false;
                if (rule.getObjectType() == ApplicableObjectType.BRAND && rule.getObjectId().equals(brandId)) return false;
            }
        }

        // 2. DUYỆT LUẬT INCLUDE (Nếu có rule Include, phải thỏa mãn ít nhất 1 rule)
        boolean hasIncludeRules = rules.stream().anyMatch(r -> r.getApplicableType() == ApplicableRuleType.INCLUDE);
        if (!hasIncludeRules) return true; // Có luật nhưng toàn Exclude, và SP này ko bị Exclude -> OK

        for (CouponApplicable rule : rules) {
            if (rule.getApplicableType() == ApplicableRuleType.INCLUDE) {
                if (rule.getObjectType() == ApplicableObjectType.PRODUCT && rule.getObjectId().equals(productId)) return true;
                if (rule.getObjectType() == ApplicableObjectType.CATEGORY && rule.getObjectId().equals(categoryId)) return true;
                if (rule.getObjectType() == ApplicableObjectType.BRAND && rule.getObjectId().equals(brandId)) return true;
            }
        }

        return false; // SP này không nằm trong danh sách Include
    }
}