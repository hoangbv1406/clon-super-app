package com.project.shopapp.domains.social.mapper;

import com.project.shopapp.domains.social.dto.request.PostCreateRequest;
import com.project.shopapp.domains.social.dto.response.PostResponse;
import com.project.shopapp.domains.social.entity.SocialPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocialPostMapper {

    @Mapping(source = "user.fullName", target = "authorName")
    @Mapping(source = "user.profileImage", target = "authorAvatar")
    @Mapping(target = "linkedProductName", ignore = true) // Set ngầm qua Internal API
    @Mapping(target = "linkedProductPrice", ignore = true)
    @Mapping(target = "linkedProductThumbnail", ignore = true)
    @Mapping(target = "isLikedByCurrentUser", ignore = true)
    PostResponse toDto(SocialPost entity);

    @Mapping(target = "mediaType", ignore = true) // Map trong Service
    SocialPost toEntityFromRequest(PostCreateRequest request);
}