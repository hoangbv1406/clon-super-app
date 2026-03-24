package com.project.shopapp.domains.affiliate.mapper;

import com.project.shopapp.domains.affiliate.dto.response.AffiliateLinkResponse;
import com.project.shopapp.domains.affiliate.entity.AffiliateLink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AffiliateLinkMapper {

    @Mapping(target = "productName", ignore = true) // Sẽ fill bằng Catalog Internal API
    @Mapping(target = "shortUrl", ignore = true)    // Sẽ được Service gen động
    AffiliateLinkResponse toDto(AffiliateLink entity);
}