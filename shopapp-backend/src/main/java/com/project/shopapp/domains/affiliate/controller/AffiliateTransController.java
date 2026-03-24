package com.project.shopapp.domains.affiliate.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.affiliate.dto.nested.AffiliateDashboardDto;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateTransResponse;
import com.project.shopapp.domains.affiliate.service.AffiliateTransService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/affiliates")
@RequiredArgsConstructor
public class AffiliateTransController {

    private final AffiliateTransService transService;
    private final SecurityUtils securityUtils;

    @GetMapping("/transactions/mine")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<PageResponse<AffiliateTransResponse>>> getMyTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(transService.getMyTransactions(userId, page, size)));
    }

    @GetMapping("/dashboard/mine")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<AffiliateDashboardDto>> getMyDashboard() {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(transService.getMyDashboard(userId)));
    }
}