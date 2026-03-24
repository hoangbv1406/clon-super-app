package com.project.shopapp.domains.affiliate.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.affiliate.dto.request.AffiliateLinkCreateRequest;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateLinkResponse;
import com.project.shopapp.domains.affiliate.service.AffiliateLinkService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AffiliateLinkController {

    private final AffiliateLinkService linkService;
    private final SecurityUtils securityUtils;

    // KOC: Tạo link tiếp thị
    @PostMapping("/affiliates/links")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<AffiliateLinkResponse>> generateLink(
            @Valid @RequestBody AffiliateLinkCreateRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                linkService.generateLink(userId, request), "Tạo Link Affiliate thành công"
        ));
    }

    // KOC: Xem báo cáo chiến dịch của mình
    @GetMapping("/affiliates/links/mine")
    @PreAuthorize("hasAnyRole('USER', 'KOC')")
    public ResponseEntity<ResponseObject<PageResponse<AffiliateLinkResponse>>> getMyLinks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(linkService.getMyLinks(userId, page, size)));
    }

    // PUBLIC: API Điều hướng (Redirect). Người dùng click link: api/v1/go/ABCXYZ
    @GetMapping("/go/{code}")
    public ResponseEntity<Void> redirectAffiliate(
            @PathVariable String code,
            HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        String redirectUrl = linkService.resolveRedirectUrl(code, ip, userAgent);

        // HTTP 302: Found (Chuyển hướng trình duyệt)
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build();
    }
}