package com.project.shopapp.domains.affiliate.mapper;

import com.project.shopapp.domains.affiliate.dto.request.ShowcaseAddRequest;
import com.project.shopapp.domains.affiliate.dto.response.ShowcaseItemResponse;
import com.project.shopapp.domains.affiliate.entity.UserShowcaseItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShowcaseItemMapper {

    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productThumbnail", ignore = true)
    @Mapping(target = "productPrice", ignore = true)
    @Mapping(target = "affiliateShortUrl", ignore = true)
    ShowcaseItemResponse toDto(UserShowcaseItem entity);

    UserShowcaseItem toEntityFromRequest(ShowcaseAddRequest request);
}