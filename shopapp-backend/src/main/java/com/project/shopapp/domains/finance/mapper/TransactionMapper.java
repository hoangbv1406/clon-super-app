package com.project.shopapp.domains.finance.mapper;

import com.project.shopapp.domains.finance.dto.response.TransactionResponse;
import com.project.shopapp.domains.finance.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionResponse toDto(Transaction entity);
}