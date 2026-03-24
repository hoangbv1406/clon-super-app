package com.project.shopapp.domains.inventory.controller;

import com.project.shopapp.domains.identity.security.SecurityUtils;
import com.project.shopapp.domains.inventory.dto.request.TransactionDetailCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionDetailResponse;
import com.project.shopapp.domains.inventory.service.TransactionDetailService;
import com.project.shopapp.shared.base.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor/shops/{shopId}/inventory-transactions/{transactionId}/details")
@RequiredArgsConstructor
public class TransactionDetailController {

    private final TransactionDetailService detailService;
    private final SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<List<TransactionDetailResponse>>> getDetails(
            @PathVariable Long transactionId) {
        return ResponseEntity.ok(ResponseObject.success(detailService.getDetailsByTransaction(transactionId)));
    }

    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<List<TransactionDetailResponse>>> addDetails(
            @PathVariable Integer shopId,
            @PathVariable Long transactionId,
            @RequestBody List<@Valid TransactionDetailCreateRequest> requests) {

        // Nhận 1 list để người dùng dùng súng tít mã vạch bắn liên tục 10 món rồi ấn "Lưu" 1 lần
        return ResponseEntity.ok(ResponseObject.created(
                detailService.addDetailsToTransaction(transactionId, shopId, requests),
                "Thêm chi tiết vào phiếu thành công"
        ));
    }

    @DeleteMapping("/{detailId}")
    @PreAuthorize("hasAnyRole('VENDOR', 'MANAGER', 'WAREHOUSE')")
    public ResponseEntity<ResponseObject<Void>> removeDetail(
            @PathVariable Integer shopId,
            @PathVariable Long transactionId,
            @PathVariable Long detailId) {

        detailService.removeDetail(transactionId, shopId, detailId);
        return ResponseEntity.ok(ResponseObject.success(null, "Xóa dòng hàng khỏi phiếu thành công"));
    }
}