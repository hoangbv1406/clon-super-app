package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.response.UserSessionResponse;
import com.project.shopapp.domains.identity.entity.UserSession;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSessionMapper extends BaseMapper<UserSessionResponse, UserSession> {
}