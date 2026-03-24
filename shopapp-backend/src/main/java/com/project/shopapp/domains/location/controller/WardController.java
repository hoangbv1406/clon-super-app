package com.project.shopapp.domains.location.controller;

import com.project.shopapp.domains.location.dto.request.WardStatusUpdateRequest;
import com.project.shopapp.domains.location.dto.response.WardResponse;
import com.project.shopapp.domains.location.service.WardService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class WardController {

    private final WardService wardService;

    // Lấy xã dựa trên huyện
    @GetMapping("/wards")
    public ResponseEntity<ResponseObject<List<WardResponse>>> getWardsByDistrict(
            @RequestParam(name = "districtCode") String districtCode) {
        return ResponseEntity.ok(ResponseObject.success(wardService.getActiveWardsByDistrict(districtCode)));
    }

    // Admin Update trạng thái bão lũ (Chỉ ADMIN được quyền)
    @PatchMapping("/wards/{code}/delivery-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseObject<WardResponse>> updateDeliveryStatus(
            @PathVariable String code,
            @Valid @RequestBody WardStatusUpdateRequest request) {
        return ResponseEntity.ok(ResponseObject.success(
                wardService.updateDeliveryStatus(code, request),
                "Cập nhật trạng thái vận chuyển thành công"
        ));
    }
}