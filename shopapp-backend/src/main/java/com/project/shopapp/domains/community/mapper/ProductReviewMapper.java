package com.project.shopapp.domains.community.mapper;

import com.project.shopapp.domains.community.dto.request.ReviewCreateRequest;
import com.project.shopapp.domains.community.dto.response.ReviewResponse;
import com.project.shopapp.domains.community.entity.ProductReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductReviewMapper {

    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.profileImage", target = "userAvatar")
    @Mapping(source = "orderDetail.variantName", target = "variantName")
    @Mapping(target = "shopReply", ignore = true) // Map thủ công trong Service nếu có
    ReviewResponse toDto(ProductReview entity);

    @Mapping(target = "status", ignore = true)
    ProductReview toEntityFromRequest(ReviewCreateRequest request);
}