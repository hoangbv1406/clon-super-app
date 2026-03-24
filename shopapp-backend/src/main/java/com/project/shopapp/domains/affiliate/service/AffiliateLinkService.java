// --- service/AffiliateLinkService.java ---
package com.project.shopapp.domains.affiliate.service;
import com.project.shopapp.domains.affiliate.dto.request.AffiliateLinkCreateRequest;
import com.project.shopapp.domains.affiliate.dto.response.AffiliateLinkResponse;
import com.project.shopapp.shared.base.PageResponse;

public interface AffiliateLinkService {
    AffiliateLinkResponse generateLink(Integer userId, AffiliateLinkCreateRequest request);
    PageResponse<AffiliateLinkResponse> getMyLinks(Integer userId, int page, int size);

    // Hàm thực thi khi User click vào link
    String resolveRedirectUrl(String code, String ipAddress, String userAgent);
}