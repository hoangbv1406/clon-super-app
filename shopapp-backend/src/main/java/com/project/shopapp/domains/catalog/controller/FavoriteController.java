package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.catalog.dto.request.FavoriteRequest;
import com.project.shopapp.domains.catalog.dto.response.FavoriteResponse;
import com.project.shopapp.domains.catalog.service.FavoriteService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final SecurityUtils securityUtils;

    @PostMapping("/toggle")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<Void>> toggleFavorite(@Valid @RequestBody FavoriteRequest request) {
        Integer userId = securityUtils.getLoggedInUserId();
        favoriteService.toggleFavorite(userId, request);
        return ResponseEntity.ok(ResponseObject.success(null, "Đã cập nhật danh sách yêu thích"));
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject<PageResponse<FavoriteResponse>>> getMyFavorites(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Integer userId = securityUtils.getLoggedInUserId();
        return ResponseEntity.ok(ResponseObject.success(favoriteService.getMyFavorites(userId, page, size)));
    }
}