package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.dto.request.UserLoginRequest;
import com.project.shopapp.domains.identity.dto.response.LoginResponse;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.identity.entity.UserSession;
import com.project.shopapp.domains.identity.mapper.UserMapper;
import com.project.shopapp.domains.identity.repository.UserRepository;
import com.project.shopapp.domains.identity.repository.UserSessionRepository;
import com.project.shopapp.domains.identity.security.JwtTokenUtils;
import com.project.shopapp.domains.identity.service.AuthService;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserMapper userMapper;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_TIME_DURATION_MINUTES = 15;

    @Override
    @Transactional(noRollbackFor = BadCredentialsException.class)
    public LoginResponse login(UserLoginRequest request, String ipAddress, String deviceId) {

        // 1. Tìm user theo Email hoặc SĐT
        User user = userRepository.findByEmailOrPhoneNumber(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Tài khoản hoặc mật khẩu không chính xác"));

        // 2. Kiểm tra tài khoản có bị khóa/vô hiệu hóa không
        if (user.getIsDeleted() > 0 || !user.getIsActive()) {
            throw new ForbiddenException("Tài khoản đã bị vô hiệu hóa hoặc bị xóa khỏi hệ thống.");
        }

        if (user.getLockedUntil() != null && user.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new ForbiddenException("Tài khoản đang bị khóa do nhập sai quá nhiều lần. Thử lại sau 15 phút.");
        }

        try {
            // 3. Thực hiện xác thực Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 4. Nếu thành công -> Reset số lần đăng nhập sai (nếu có)
            if (user.getFailedLoginAttempts() > 0 || user.getLockedUntil() != null) {
                user.setFailedLoginAttempts(0);
                user.setLockedUntil(null);
                userRepository.save(user);
            }

            // 5. Generate Access Token & Refresh Token
            String accessToken = jwtTokenUtils.generateToken(user);
            String refreshToken = UUID.randomUUID().toString();

            // 6. Lưu Session vào DB
            // [FIXED QUAN TRỌNG]: Dùng .userId(user.getId()) để khớp với cấu trúc Entity của cậu
            UserSession session = UserSession.builder()
                    .userId(user.getId())
                    .refreshTokenHash(refreshToken)
                    .deviceId(deviceId)
                    .ipAddress(ipAddress)
                    .expiresAt(LocalDateTime.now().plusDays(7)) // Refresh token sống 7 ngày
                    .isRevoked(false)
                    .build();

            userSessionRepository.save(session);

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(userMapper.toDto(user))
                    .build();

        } catch (BadCredentialsException ex) {
            // 7. Xử lý khi sai mật khẩu -> Tăng số lần đăng nhập sai
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCK_TIME_DURATION_MINUTES));
                log.warn("Account {} locked due to brute-force attack.", user.getEmail());
            }
            userRepository.save(user);

            throw new BadCredentialsException("Tài khoản hoặc mật khẩu không chính xác");
        }
    }
}
