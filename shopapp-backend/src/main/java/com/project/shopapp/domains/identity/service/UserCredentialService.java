package com.project.shopapp.domains.identity.service;
import com.project.shopapp.domains.identity.dto.request.CredentialRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.CredentialResponse;
import java.util.List;

public interface UserCredentialService {
    List<CredentialResponse> getUserCredentials(Integer userId);
    CredentialResponse registerCredential(Integer userId, CredentialRegisterRequest request);
    void revokeCredential(Integer userId, Integer credentialId);
    void updateSignCount(String credentialId, int incomingSignCount);
}