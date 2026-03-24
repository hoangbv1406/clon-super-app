package com.project.shopapp.domains.catalog.mapper;

import com.project.shopapp.domains.catalog.dto.request.FavoriteRequest;
import com.project.shopapp.domains.catalog.dto.response.FavoriteResponse;
import com.project.shopapp.domains.catalog.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "productThumbnail", ignore = true)
    @Mapping(target = "productPrice", ignore = true)
    @Mapping(target = "productSlug", ignore = true)
    @Mapping(target = "isOutOfStock", ignore = true)
    FavoriteResponse toDto(Favorite entity);

    Favorite toEntityFromRequest(FavoriteRequest request);
}