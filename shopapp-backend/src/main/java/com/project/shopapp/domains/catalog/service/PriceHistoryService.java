package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.nested.PriceChartPointDto;
import com.project.shopapp.domains.catalog.dto.response.PriceHistoryResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.util.List;

public interface PriceHistoryService {
    // Cho Admin xem dạng Table phân trang
    PageResponse<PriceHistoryResponse> getPriceLogs(Integer productId, int page, int size);

    // Cho Frontend vẽ biểu đồ đường (Line Chart)
    List<PriceChartPointDto> getPriceChart(Integer productId, Integer variantId);
}