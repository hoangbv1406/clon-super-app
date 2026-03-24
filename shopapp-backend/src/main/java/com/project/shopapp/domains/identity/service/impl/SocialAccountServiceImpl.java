package com.project.shopapp.domains.identity.service.impl;

import com.project.shopapp.domains.identity.api.RoleInternalApi;
import com.project.shopapp.domains.identity.dto.request.SocialAuthRequest;
import com.project.shopapp.domains.identity.dto.response.SocialAccountResponse;
import com.project.shopapp.domains.identity.dto.response.TokenResponse;
import com.project.shopapp.domains.identity.entity.SocialAccount;
import com.project.shopapp.domains.identity.entity.User;
import com.project.shopapp.domains.identity.enums.SocialProvider;
import com.project.shopapp.domains.identity.event.SocialAccountLinkedEvent;
import com.project.shopapp.domains.identity.mapper.SocialAccountMapper;
import com.project.shopapp.domains.identity.repository.SocialAccountRepository;
import com.project.shopapp.domains.identity.repository.UserRepository;
import com.project.shopapp.domains.identity.service.SocialAccountService;
import com.project.shopapp.domains.identity.service.UserSessionService;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import com.project.shopapp.shared.exceptions.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialAccountServiceImpl implements SocialAccountService {

    private final SocialAccountRepository socialRepository;
    private final UserRepository userRepository;
    private final UserSessionService sessionService;
    private final SocialAccountMapper socialMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final RoleInternalApi roleInternalApi;

    // TODO: Cần Inject GoogleAuthClient và FacebookAuthClient (từ thư mục external) để verify token

    @Override
    @Transactional
    public TokenResponse loginOrRegisterWithSocial(SocialAuthRequest request, String ipAddress, String userAgent, String deviceId) {
        SocialProvider provider = SocialProvider.valueOf(request.getProvider().toUpperCase());

        // 1. Verify token với Google/Facebook (Mock logic)
        // SocialUserInfo userInfo = googleAuthClient.verifyToken(request.getAccessToken());
        // Giả lập DTO trả về từ Google:
        String googleId = "1029384756";
        String googleEmail = "customer@gmail.com";
        String googleName = "Khách Hàng VIP";
        String googleAvatar = "https://lh3.googleusercontent.com/a/avatar";

        // 2. Kiểm tra xem Social Account này đã tồn tại chưa
        SocialAccount socialAccount = socialRepository.findByProviderAndProviderId(provider, googleId).orElse(null);
        User user;

        if (socialAccount != null) {
            // Đã từng đăng nhập bằng MXH này rồi
            user = userRepository.findById(socialAccount.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("Dữ liệu user bị lỗi"));

            // Cập nhật lại avatar và tên mới nhất từ Google
            socialAccount.setAvatarUrl(googleAvatar);
            socialAccount.setName(googleName);
            socialRepository.save(socialAccount);
        } else {
            // Lần đầu dùng MXH này. Kiểm tra xem Email này đã đăng ký tài khoản thường chưa?
            user = userRepository.findByEmailAndIsDeleted(googleEmail, 0L).orElse(null);

            if (user == null) {
                // TẠO TÀI KHOẢN MỚI HOÀN TOÀN
                user = User.builder()
                        .email(googleEmail)
                        .fullName(googleName)
                        .profileImage(googleAvatar)
                        .roleId(roleInternalApi.getDefaultRoleId("user")) // Role mặc định
                        .build();
                user = userRepository.save(user);
            }

            // TẠO LIÊN KẾT MXH
            socialAccount = SocialAccount.builder()
                    .userId(user.getId())
                    .provider(provider)
                    .providerId(googleId)
                    .email(googleEmail)
                    .name(googleName)
                    .avatarUrl(googleAvatar)
                    .build();
            socialRepository.save(socialAccount);
        }

        if (!user.getIsActive()) {
            throw new ForbiddenException("Tài khoản của bạn đã bị vô hiệu hóa.");
        }

        // 3. Sinh JWT Token và Session trả về cho FE
        return sessionService.createNewSession(user.getId(), ipAddress, userAgent, deviceId);
    }

    @Override
    @Transactional
    public void linkSocialAccount(Integer userId, SocialAuthRequest request) {
        SocialProvider provider = SocialProvider.valueOf(request.getProvider().toUpperCase());

        if (socialRepository.existsByUserIdAndProvider(userId, provider)) {
            throw new ConflictException("Bạn đã liên kết tài khoản " + provider + " rồi.");
        }

        // TODO: Verify token và lấy thông tin như hàm login...
        String verifiedId = "999888777";
        String verifiedEmail = "my.email@gmail.com";

        SocialAccount newLink = SocialAccount.builder()
                .userId(userId)
                .provider(provider)
                .providerId(verifiedId)
                .email(verifiedEmail)
                .name("Linked Name")
                .build();
        socialRepository.save(newLink);

        eventPublisher.publishEvent(new SocialAccountLinkedEvent(userId, provider.name(), verifiedEmail));
    }

    @Override
    public List<SocialAccountResponse> getLinkedAccounts(Integer userId) {
        return socialRepository.findByUserId(userId).stream()
                .map(socialMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void unlinkSocialAccount(Integer userId, String providerStr) {
        SocialProvider provider = SocialProvider.valueOf(providerStr.toUpperCase());
        SocialAccount account = socialRepository.findByUserId(userId).stream()
                .filter(a -> a.getProvider() == provider)
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException("Tài khoản chưa được liên kết với " + provider));

        socialRepository.delete(account);
        // Note: Cần check nếu user chưa có Password mà xóa luôn MXH duy nhất thì sẽ không login được nữa
    }
}