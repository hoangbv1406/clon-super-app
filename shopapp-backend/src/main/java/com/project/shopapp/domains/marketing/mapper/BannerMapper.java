package com.project.shopapp.domains.marketing.mapper;

import com.project.shopapp.domains.marketing.dto.request.BannerCreateRequest;
import com.project.shopapp.domains.marketing.dto.response.BannerResponse;
import com.project.shopapp.domains.marketing.entity.Banner;
import com.project.shopapp.shared.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BannerMapper extends BaseMapper<BannerResponse, Banner> {
    Banner toEntityFromRequest(BannerCreateRequest request);
}