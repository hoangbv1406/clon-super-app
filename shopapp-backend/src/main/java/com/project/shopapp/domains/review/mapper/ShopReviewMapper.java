package com.project.shopapp.domains.review.mapper;

import com.project.shopapp.domains.review.dto.request.ShopReviewCreateRequest;
import com.project.shopapp.domains.review.dto.response.ShopReviewResponse;
import com.project.shopapp.domains.review.entity.ShopReview;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopReviewMapper extends BaseMapper<ShopReviewResponse, ShopReview> {

    @Mapping(source = "user.fullName", target = "reviewerName")
    @Mapping(source = "user.profileImage", target = "reviewerAvatar")
    // Note: Dùng logic ở Service để map phần sellerReply vào DTO để tránh vòng lặp vô tận (StackOverflow)
    @Mapping(target = "sellerReply", ignore = true)
    ShopReviewResponse toDto(ShopReview entity);

    ShopReview toEntityFromCreateRequest(ShopReviewCreateRequest request);
}