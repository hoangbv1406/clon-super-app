package com.project.shopapp.domains.identity.service;
import com.project.shopapp.domains.identity.dto.request.UserRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegisterRequest request);
    void handleFailedLogin(String email, String ipAddress);
    void resetFailedLogin(String email);
    void softDeleteUser(Integer id);
}