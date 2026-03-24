package com.project.shopapp.domains.marketing.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.marketing.dto.request.ApplicableCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.ApplicableResponse;
import com.project.shopapp.domains.marketing.service.ApplicableService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marketing/coupons/{couponId}/applicables")
@RequiredArgsConstructor
public class ApplicableController {

    private final ApplicableService applicableService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<List<ApplicableResponse>>> getRules(@PathVariable Integer couponId) {
        return ResponseEntity.ok(ResponseObject.success(applicableService.getRulesForCoupon(couponId)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<ApplicableResponse>> addRule(
            @PathVariable Integer couponId,
            @Valid @RequestBody ApplicableCreateRequest request) {

        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                applicableService.addRuleToCoupon(userId, couponId, request), "Thêm luật áp dụng thành công"
        ));
    }

    @DeleteMapping("/{ruleId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<Void>> removeRule(
            @PathVariable Integer couponId,
            @PathVariable Integer ruleId) {

        Integer userId = securityUtils.getLoggedInUserId();
        applicableService.removeRule(userId, couponId, ruleId);
        return ResponseEntity.ok(ResponseObject.success(null, "Xóa luật áp dụng thành công"));
    }
}