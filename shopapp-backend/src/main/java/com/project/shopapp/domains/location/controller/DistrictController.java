package com.project.shopapp.domains.location.controller;

import com.project.shopapp.domains.location.dto.response.DistrictResponse;
import com.project.shopapp.domains.location.service.DistrictService;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    // Lấy danh sách Huyện DỰA TRÊN MÃ TỈNH (Ví dụ: /api/v1/locations/districts?provinceCode=01)
    @GetMapping("/districts")
    public ResponseEntity<ResponseObject<List<DistrictResponse>>> getDistrictsByProvince(
            @RequestParam(name = "provinceCode") String provinceCode) {
        return ResponseEntity.ok(ResponseObject.success(districtService.getActiveDistrictsByProvince(provinceCode)));
    }

    // Lấy chi tiết 1 huyện
    @GetMapping("/districts/{code}")
    public ResponseEntity<ResponseObject<DistrictResponse>> getDistrict(@PathVariable String code) {
        return ResponseEntity.ok(ResponseObject.success(districtService.getDistrictByCode(code)));
    }
}