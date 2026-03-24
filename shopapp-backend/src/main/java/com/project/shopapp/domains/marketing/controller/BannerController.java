package com.project.shopapp.domains.marketing.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.marketing.dto.request.BannerCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.BannerResponse;
import com.project.shopapp.domains.marketing.service.BannerService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marketing/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;
    private final SecurityUtils securityUtils;

    // PUBLIC: Mở app là gọi API này
    @GetMapping("/active")
    public ResponseEntity<ResponseObject<List<BannerResponse>>> getActiveBanners(
            @RequestParam(defaultValue = "HOME_MAIN") String position,
            @RequestParam(defaultValue = "ALL") String platform) {
        return ResponseEntity.ok(ResponseObject.success(bannerService.getActiveBanners(position, platform)));
    }

    // PUBLIC: Click tracking (Chỉ trả về OK, không chờ tính toán)
    @PostMapping("/{id}/click")
    public ResponseEntity<Void> trackClick(@PathVariable Integer id) {
        bannerService.trackClick(id);
        return ResponseEntity.accepted().build(); // HTTP 202 Accepted
    }

    // ADMIN CMS: Quản trị
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<PageResponse<BannerResponse>>> getBannersForAdmin(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String position,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(
                bannerService.searchBannersForAdmin(title, position, page, size)
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<BannerResponse>> createBanner(@Valid @RequestBody BannerCreateRequest request) {
        Integer adminId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.created(
                bannerService.createBanner(adminId, request), "Thêm Banner thành công"
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<Void>> deleteBanner(@PathVariable Integer id) {
        Integer adminId = securityUtils.getLoggedInUserId();
        bannerService.deleteBanner(adminId, id);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã xóa banner"));
    }
}