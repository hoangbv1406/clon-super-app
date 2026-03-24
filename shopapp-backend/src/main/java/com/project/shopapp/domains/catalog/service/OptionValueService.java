package com.project.shopapp.domains.catalog.service;
import com.project.shopapp.domains.catalog.dto.request.OptionValueCreateRequest;
import com.project.shopapp.domains.catalog.dto.response.OptionValueResponse;
import com.project.shopapp.shared.base.PageResponse;
import java.util.List;

public interface OptionValueService {
    List<OptionValueResponse> getActiveValuesByOption(Integer optionId);
    PageResponse<OptionValueResponse> getValuesForAdmin(Integer optionId, String keyword, int page, int size);
    OptionValueResponse createOptionValue(Integer adminId, OptionValueCreateRequest request);
    void deleteOptionValue(Integer adminId, Integer id);
}