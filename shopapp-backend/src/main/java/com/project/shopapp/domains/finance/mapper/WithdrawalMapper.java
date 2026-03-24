package com.project.shopapp.domains.finance.mapper;

import com.project.shopapp.domains.finance.dto.request.WithdrawalCreateRequest;
import com.project.shopapp.domains.finance.dto.response.WithdrawalResponse;
import com.project.shopapp.domains.finance.entity.WithdrawalRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WithdrawalMapper {

    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "approver.fullName", target = "approverName")
    WithdrawalResponse toDto(WithdrawalRequest entity);

    WithdrawalRequest toEntityFromRequest(WithdrawalCreateRequest request);
}