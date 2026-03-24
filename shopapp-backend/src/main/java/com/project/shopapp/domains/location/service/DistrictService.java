package com.project.shopapp.domains.location.service;
import com.project.shopapp.domains.location.dto.response.DistrictResponse;
import java.util.List;

public interface DistrictService {
    List<DistrictResponse> getActiveDistrictsByProvince(String provinceCode);
    DistrictResponse getDistrictByCode(String code);
}