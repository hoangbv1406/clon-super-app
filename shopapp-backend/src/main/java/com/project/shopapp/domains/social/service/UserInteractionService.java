// --- service/UserInteractionService.java ---
package com.project.shopapp.domains.social.service;
import com.project.shopapp.domains.social.dto.request.InteractionLogRequest;

public interface UserInteractionService {
    void logInteraction(Integer userId, String ipAddress, InteractionLogRequest request);
}