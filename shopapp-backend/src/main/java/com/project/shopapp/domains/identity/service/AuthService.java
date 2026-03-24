package com.project.shopapp.domains.identity.service;

import com.project.shopapp.domains.identity.dto.request.UserLoginRequest;
import com.project.shopapp.domains.identity.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(UserLoginRequest request, String ipAddress, String deviceId);
}
