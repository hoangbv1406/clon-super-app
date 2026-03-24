package com.project.shopapp.domains.finance.mapper;

import com.project.shopapp.domains.finance.dto.response.WalletResponse;
import com.project.shopapp.domains.finance.entity.Wallet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    WalletResponse toDto(Wallet entity);
}