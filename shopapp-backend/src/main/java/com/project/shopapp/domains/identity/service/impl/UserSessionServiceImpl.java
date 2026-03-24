package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.constant.SessionConstants;
import com.project.shopapp.domains.identity.dto.request.RefreshTokenRequest;
import com.project.shopapp.domains.identity.dto.response.TokenResponse;
import com.project.shopapp.domains.identity.dto.response.UserSessionResponse;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.identity.entity.UserSession;
import com.project.shopapp.domains.identity.event.SuspiciousActivityEvent;
import com.project.shopapp.domains.identity.repository.UserRepository;
import com.project.shopapp.domains.identity.repository.UserSessionRepository;
import com.project.shopapp.domains.identity.security.JwtTokenUtils;
import com.project.shopapp.domains.identity.service.UserSessionService;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import com.project.shopapp.shared.exceptions.ExpiredTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSessionServiceImpl implements UserSessionService {

    private final UserSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TokenResponse createNewSession(Integer userId, String ipAddress, String userAgent, String deviceId) {
        User user = userRepository.findById(userId).orElseThrow();

        String accessToken = jwtTokenUtils.generateToken(user);
        String rawRefreshToken = UUID.randomUUID().toString();
        String hashedRefreshToken = passwordEncoder.encode(rawRefreshToken);

        UserSession session = UserSession.builder()
                .userId(userId)
                .refreshTokenHash(hashedRefreshToken)
                .deviceId(deviceId)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                // Dùng hằng số từ SessionConstants thay vì hardcode số 7
                .expiresAt(LocalDateTime.now().plusDays(SessionConstants.REFRESH_TOKEN_EXPIRATION_DAYS))
                .build();

        // [FIX BUGS]: Phải save xuống DB trước để lấy ID do Auto Increment sinh ra
        session = sessionRepository.save(session);

        return TokenResponse.builder()
                .accessToken(accessToken)
                // [FIX BUGS]: Format chuẩn "SessionID.UUID" để hàm refreshToken bên dưới có thể split được
                .refreshToken(session.getId() + "." + rawRefreshToken)
                .tokenType("Bearer")
                .expiresIn(3600L) // (Có thể gom số 3600 này vào JwtConstants sau)
                .build();
    }

    @Override
    @Transactional
    public TokenResponse refreshToken(RefreshTokenRequest request, String ipAddress, String userAgent) {
        String rawToken = request.getRefreshToken();

        // Parse Token theo chuẩn "SessionID.UUID"
        String[] parts = rawToken.split("\\.");
        if (parts.length != 2) {
            throw new ForbiddenException("Invalid Refresh Token format");
        }

        Long sessionId = Long.parseLong(parts[0]);
        String rawUuid = parts[1];

        UserSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ForbiddenException("Session không tồn tại"));

        if (!passwordEncoder.matches(rawUuid, session.getRefreshTokenHash())) {
            throw new ForbiddenException("Refresh Token không hợp lệ");
        }

        if (session.getIsRevoked()) {
            sessionRepository.revokeAllUserSessions(session.getUserId());
            eventPublisher.publishEvent(new SuspiciousActivityEvent(session.getUserId(), ipAddress, "Phát hiện tái sử dụng Token đã thu hồi"));
            throw new ForbiddenException("Phát hiện truy cập bất hợp pháp. Toàn bộ tài khoản đã bị đăng xuất.");
        }

        if (session.isExpired()) {
            throw new ExpiredTokenException("Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
        }

        // --- ROTATE TOKEN ---
        String newRawUuid = UUID.randomUUID().toString();
        String newHashedToken = passwordEncoder.encode(newRawUuid);

        session.setReplacedByTokenHash(newHashedToken);
        session.setIsRevoked(true);
        sessionRepository.save(session);

        UserSession newSession = UserSession.builder()
                .userId(session.getUserId())
                .refreshTokenHash(newHashedToken)
                .deviceId(session.getDeviceId())
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                // Dùng hằng số từ SessionConstants
                .expiresAt(LocalDateTime.now().plusDays(SessionConstants.REFRESH_TOKEN_EXPIRATION_DAYS))
                .build();

        UserSession savedNewSession = sessionRepository.save(newSession);
        User user = userRepository.findById(session.getUserId()).orElseThrow();
        String newAccessToken = jwtTokenUtils.generateToken(user);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(savedNewSession.getId() + "." + newRawUuid)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSessionResponse> getActiveSessions(Integer userId, String currentTokenHash) {
        return sessionRepository.findByUserIdAndIsRevokedFalseAndExpiresAtAfter(userId, LocalDateTime.now())
                .stream().map(session -> UserSessionResponse.builder()
                        .id(session.getId())
                        .deviceId(session.getDeviceId())
                        .ipAddress(session.getIpAddress())
                        .userAgent(session.getUserAgent())
                        .createdAt(session.getCreatedAt())
                        .isCurrentSession(passwordEncoder.matches(currentTokenHash, session.getRefreshTokenHash()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void revokeSession(Integer userId, Long sessionId) {
        UserSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ForbiddenException("Session không tồn tại"));

        if (!session.getUserId().equals(userId)) {
            throw new ForbiddenException("Bạn không có quyền thao tác trên session này");
        }

        session.setIsRevoked(true);
        sessionRepository.save(session);
    }
}
