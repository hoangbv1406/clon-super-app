package com.project.shopapp.domains.location.controller;

import com.project.shopapp.domains.location.dto.response.ProvinceResponse;
import com.project.shopapp.domains.location.service.ProvinceService;
import com.project.shopapp.shared.base.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<ResponseObject<List<ProvinceResponse>>> getAllProvinces() {
        return ResponseEntity.ok(ResponseObject.success(provinceService.getAllActiveProvinces()));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ResponseObject<ProvinceResponse>> getProvince(@PathVariable String code) {
        return ResponseEntity.ok(ResponseObject.success(provinceService.getProvinceByCode(code)));
    }
}