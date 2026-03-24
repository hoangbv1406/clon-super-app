// --- service/AffiliateTransService.java ---
package com.project.shopapp.domains.affiliate.service;
import com.project.shopapp.domains.affiliate.dto.nested.AffiliateDashboardDto;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateTransResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface AffiliateTransService {
    PageResponse<AffiliateTransResponse> getMyTransactions(Integer userId, int page, int size);
    AffiliateDashboardDto getMyDashboard(Integer userId);
}