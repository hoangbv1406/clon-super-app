package com.project.shopapp.domains.social.mapper;

import com.project.shopapp.domains.social.dto.response.FollowResponse;
import com.project.shopapp.domains.social.entity.UserFollow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserFollowMapper {
    // Tớ sẽ xử lý map tay trong Service vì logic map tên (TargetName)
    // phụ thuộc vào việc là Shop hay là User. Dùng @Mapping annotation sẽ khá phức tạp và khó debug.
}