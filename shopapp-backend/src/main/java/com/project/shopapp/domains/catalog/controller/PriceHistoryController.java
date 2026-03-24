package com.project.shopapp.domains.catalog.controller;

import com.project.shopapp.domains.catalog.dto.nested.PriceChartPointDto;
import com.project.shopapp.domains.catalog.dto.response.PriceHistoryResponse;
import com.project.shopapp.domains.catalog.service.PriceHistoryService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class PriceHistoryController {

    private final PriceHistoryService historyService;

    // ADMIN/VENDOR: Xem lịch sử chi tiết
    @GetMapping("/products/{productId}/price-histories")
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public ResponseEntity<ResponseObject<PageResponse<PriceHistoryResponse>>> getPriceLogs(
            @PathVariable Integer productId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ResponseObject.success(historyService.getPriceLogs(productId, page, size)));
    }

    // PUBLIC: Lấy dữ liệu vẽ biểu đồ giá cho Frontend
    @GetMapping("/products/{productId}/price-chart")
    public ResponseEntity<ResponseObject<List<PriceChartPointDto>>> getPriceChart(
            @PathVariable Integer productId,
            @RequestParam(required = false) Integer variantId) {
        return ResponseEntity.ok(ResponseObject.success(historyService.getPriceChart(productId, variantId)));
    }
}