package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.api.RoleInternalApi;
import com.project.shopapp.domains.identity.dto.request.UserRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.UserResponse;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.identity.event.UserLockedEvent;
import com.project.shopapp.domains.identity.event.UserRegisteredEvent;
import com.project.shopapp.domains.identity.mapper.UserMapper;
import com.project.shopapp.domains.identity.repository.UserRepository;
import com.project.shopapp.domains.identity.service.UserService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final RoleInternalApi roleInternalApi; // Gọi Facade để lấy Role ID

    @Override
    @Transactional
    public UserResponse registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmailAndIsDeleted(request.getEmail(), 0L)) {
            throw new ConflictException("Email đã được sử dụng");
        }

        User user = userMapper.toEntityFromRegisterRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Mặc định gán role User
        user.setRoleId(roleInternalApi.getDefaultRoleId("user"));

        User savedUser = userRepository.save(user);

        // BẮN EVENT: Để Module Wallet tạo ví, Module Mail gửi OTP
        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail(), savedUser.getFullName()));

        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public void handleFailedLogin(String email, String ipAddress) {
        User user = userRepository.findByEmailAndIsDeleted(email, 0L).orElse(null);
        if (user == null) return;

        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        if (user.getFailedLoginAttempts() >= 5) {
            user.setLockedUntil(LocalDateTime.now().plusMinutes(15));
            userRepository.save(user);
            // Bắn event cảnh báo bảo mật
            eventPublisher.publishEvent(new UserLockedEvent(email, ipAddress));
        } else {
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void resetFailedLogin(String email) {
        userRepository.resetFailedLogins(email);
    }

    @Override
    @Transactional
    public void softDeleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("User không tồn tại"));
        // Trick của E-commerce: Gán isDeleted = currentTimeMillis để nhả Unique Email ra
        user.setIsDeleted(System.currentTimeMillis());
        user.setDeletedAt(LocalDateTime.now());
        user.setIsActive(false);
        userRepository.save(user);
    }
}