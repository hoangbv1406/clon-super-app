package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.dto.request.CredentialRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.CredentialResponse;
import com.project.shopapp.domains.identity.entity.UserCredential;
import com.project.shopapp.domains.identity.event.ClonedPasskeyDetectedEvent;
import com.project.shopapp.domains.identity.event.PasskeyRegisteredEvent;
import com.project.shopapp.domains.identity.mapper.UserCredentialMapper;
import com.project.shopapp.domains.identity.repository.UserCredentialRepository;
import com.project.shopapp.domains.identity.service.UserCredentialService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {

    private final UserCredentialRepository credentialRepository;
    private final UserCredentialMapper credentialMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public List<CredentialResponse> getUserCredentials(Integer userId) {
        return credentialRepository.findByUserIdAndIsActiveTrue(userId)
                .stream().map(credentialMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CredentialResponse registerCredential(Integer userId, CredentialRegisterRequest request) {
        if (credentialRepository.existsByCredentialId(request.getCredentialId())) {
            throw new ConflictException("Credential này đã được đăng ký trên hệ thống");
        }

        UserCredential credential = credentialMapper.toEntityFromRequest(request);
        credential.setUserId(userId);
        UserCredential saved = credentialRepository.save(credential);

        // Bắn Event để gửi Security Alert Email
        eventPublisher.publishEvent(new PasskeyRegisteredEvent(userId, saved.getDeviceLabel()));

        return credentialMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void revokeCredential(Integer userId, Integer credentialId) {
        UserCredential credential = credentialRepository.findById(credentialId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy thông tin đăng nhập"));

        if (!credential.getUserId().equals(userId)) {
            throw new ForbiddenException("Bạn không có quyền thu hồi thiết bị của người khác");
        }

        credential.setIsActive(false);
        credentialRepository.save(credential);
    }

    @Override
    @Transactional
    public void updateSignCount(String credentialId, int incomingSignCount) {
        UserCredential credential = credentialRepository.findByCredentialIdAndIsActiveTrue(credentialId)
                .orElseThrow(() -> new DataNotFoundException("Credential không hợp lệ hoặc đã bị khóa"));

        // LOGIC CHỐNG TẤN CÔNG NHÂN BẢN CỦA WEBAUTHN (FIDO2 Standard)
        if (incomingSignCount > 0 && incomingSignCount <= credential.getSignCount()) {
            // Nguy hiểm! Một thiết bị nhân bản đang cố đăng nhập với sign count cũ
            credential.setIsActive(false);
            credentialRepository.save(credential);

            // Bắn event để khóa ngay lập tức tài khoản User
            eventPublisher.publishEvent(new ClonedPasskeyDetectedEvent(credential.getUserId(), credentialId));
            throw new ForbiddenException("Phát hiện nguy cơ bảo mật nghiêm trọng. Passkey đã bị vô hiệu hóa.");
        }

        credential.setSignCount(incomingSignCount);
        credential.setLastUsedAt(LocalDateTime.now());
        credentialRepository.save(credential);
    }
}