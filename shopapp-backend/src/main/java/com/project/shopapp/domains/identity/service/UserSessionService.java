package com.project.shopapp.domains.identity.service;
import com.project.shopapp.domains.identity.dto.request.RefreshTokenRequest;
import com.project.shopapp.domains.identity.dto.response.TokenResponse;
import com.project.shopapp.domains.identity.dto.response.UserSessionResponse;
import java.util.List;

public interface UserSessionService {
    TokenResponse createNewSession(Integer userId, String ipAddress, String userAgent, String deviceId);
    TokenResponse refreshToken(RefreshTokenRequest request, String ipAddress, String userAgent);
    List<UserSessionResponse> getActiveSessions(Integer userId, String currentTokenHash);
    void revokeSession(Integer userId, Long sessionId);
}