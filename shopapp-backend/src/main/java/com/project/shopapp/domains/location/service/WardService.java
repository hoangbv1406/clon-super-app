package com.project.shopapp.domains.location.service;
import com.project.shopapp.domains.location.dto.request.WardStatusUpdateRequest;
import com.project.shopapp.domains.location.dto.response.WardResponse;
import java.util.List;

public interface WardService {
    List<WardResponse> getActiveWardsByDistrict(String districtCode);
    WardResponse updateDeliveryStatus(String code, WardStatusUpdateRequest request);
}