package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.request.CredentialRegisterRequest;
import com.project.shopapp.domains.identity.dto.response.CredentialResponse;
import com.project.shopapp.domains.identity.entity.UserCredential;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCredentialMapper extends BaseMapper<CredentialResponse, UserCredential> {
    UserCredential toEntityFromRequest(CredentialRegisterRequest request);
}