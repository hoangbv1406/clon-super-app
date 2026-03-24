package com.project.shopapp.domains.finance.mapper;

import com.project.shopapp.domains.finance.dto.response.WalletTransResponse;
import com.project.shopapp.domains.finance.entity.WalletTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletTransactionMapper {
    WalletTransResponse toDto(WalletTransaction entity);
}