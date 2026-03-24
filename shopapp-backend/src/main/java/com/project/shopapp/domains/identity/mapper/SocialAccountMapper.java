package com.project.shopapp.domains.identity.mapper;

import com.project.shopapp.domains.identity.dto.response.SocialAccountResponse;
import com.project.shopapp.domains.identity.entity.SocialAccount;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocialAccountMapper extends BaseMapper<SocialAccountResponse, SocialAccount> {
}