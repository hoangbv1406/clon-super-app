package com.project.shopapp.domains.marketing.repository;

import com.project.shopapp.domains.marketing.entity.CouponApplicable;
import com.project.shopapp.domains.marketing.enums.ApplicableObjectType;
import com.project.shopapp.domains.marketing.enums.ApplicableRuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponApplicableRepository extends JpaRepository<CouponApplicable, Integer>, JpaSpecificationExecutor<CouponApplicable> {

    List<CouponApplicable> findByCouponId(Integer couponId);

    // Tìm kiếm luật EXCLUDE để ưu tiên block trước khi tính INCLUDE
    List<CouponApplicable> findByCouponIdAndApplicableType(Integer couponId, ApplicableRuleType type);

    boolean existsByCouponIdAndObjectTypeAndObjectIdAndApplicableType(Integer couponId, ApplicableObjectType objType, Integer objId, ApplicableRuleType appType);
}