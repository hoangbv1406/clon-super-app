// --- service/ApplicableService.java ---
package com.project.shopapp.domains.marketing.service;
import com.project.shopapp.domains.marketing.dto.request.ApplicableCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.ApplicableResponse;
import java.util.List;

public interface ApplicableService {
    List<ApplicableResponse> getRulesForCoupon(Integer couponId);
    ApplicableResponse addRuleToCoupon(Integer adminId, Integer couponId, ApplicableCreateRequest request);
    void removeRule(Integer adminId, Integer couponId, Integer ruleId);
}