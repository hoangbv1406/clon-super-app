// --- nested/FollowCountDto.java ---
package com.project.shopapp.domains.social.dto.nested;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowCountDto {
    private long totalFollowers;
    private long totalFollowing;
}