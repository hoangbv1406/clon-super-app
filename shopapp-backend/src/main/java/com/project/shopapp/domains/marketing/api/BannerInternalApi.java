// --- api/BannerInternalApi.java ---
package com.project.shopapp.domains.marketing.api;

public interface BannerInternalApi {
    // Template để các module khác xin thông tin Banner
    String getTargetUrl(Integer bannerId);
}
