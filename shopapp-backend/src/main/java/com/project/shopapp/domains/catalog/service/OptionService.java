package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.OptionCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.util.List;

public interface OptionService {
    List<OptionResponse> getAllActiveOptions(); // Cho FE load Master Data
    PageResponse<OptionResponse> searchOptions(String keyword, int page, int size); // Cho Admin
    OptionResponse createOption(Integer adminId, OptionCreateRequest request);
    OptionResponse toggleOptionStatus(Integer adminId, Integer id, boolean isActive);
}