// --- api/CouponRuleInternalApi.java ---
package com.project.shopapp.domains.marketing.api;

public interface CouponRuleInternalApi {
    /**
     * Rule Engine: Đánh giá xem Một Sản Phẩm (và Danh mục, Thương hiệu của nó) 
     * có thỏa mãn điều kiện áp dụng của một Coupon hay không?
     */
    boolean isProductEligibleForCoupon(Integer couponId, Integer productId, Integer categoryId, Integer brandId);
}