package com.project.shopapp.domains.inventory.service;
import com.project.shopapp.domains.inventory.dto.request.TransactionDetailCreateRequest;
import com.project.shopapp.domains.inventory.dto.response.TransactionDetailResponse;
import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetailResponse> getDetailsByTransaction(Long transactionId);
    List<TransactionDetailResponse> addDetailsToTransaction(Long transactionId, Integer shopId, List<TransactionDetailCreateRequest> requests);
    void removeDetail(Long transactionId, Integer shopId, Long detailId);
}