package com.project.shopapp.domains.identity.service;

import com.project.shopapp.domains.identity.dto.request.SocialAuthRequest;
import com.project.shopapp.domains.identity.dto.response.SocialAccountResponse;
import com.project.shopapp.domains.identity.dto.response.TokenResponse;
import java.util.List;

public interface SocialAccountService {
    // Luồng 1: Khách hàng dùng mạng xã hội để Đăng nhập / Đăng ký
    TokenResponse loginOrRegisterWithSocial(SocialAuthRequest request, String ipAddress, String userAgent, String deviceId);

    // Luồng 2: Khách hàng đã đăng nhập, muốn Liên kết thêm MXH
    void linkSocialAccount(Integer userId, SocialAuthRequest request);

    // Luồng 3: Lấy danh sách tài khoản đã liên kết
    List<SocialAccountResponse> getLinkedAccounts(Integer userId);

    // Luồng 4: Gỡ liên kết
    void unlinkSocialAccount(Integer userId, String provider);
}