package com.project.shopapp.domains.location.service;
import com.project.shopapp.domains.location.dto.response.ProvinceResponse;
import java.util.List;

public interface ProvinceService {
    List<ProvinceResponse> getAllActiveProvinces();
    ProvinceResponse getProvinceByCode(String code);
}