package com.project.shopapp.domains.pos.mapper;

import com.project.shopapp.domains.pos.dto.response.PosSessionResponse;
import com.project.shopapp.domains.pos.entity.PosSession;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PosSessionMapper extends BaseMapper<PosSessionResponse, PosSession> {

    @Mapping(source = "user.fullName", target = "cashierName")
    PosSessionResponse toDto(PosSession entity);
}